package com.stadium_parika.services.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stadium_parika.dto.ParkingSessionDto;
import com.stadium_parika.dto.ParkingSessionRequestDto;
import com.stadium_parika.exceptions.EntityNotFoundException;
import com.stadium_parika.exceptions.ErrorCodes;
import com.stadium_parika.exceptions.InvalidEntityException;
import com.stadium_parika.model.Parking;
import com.stadium_parika.model.ParkingSession;
import com.stadium_parika.model.ParkingSessionStatusEnum;
import com.stadium_parika.model.ParkingTarif;
import com.stadium_parika.model.PaymentStatusEnum;
import com.stadium_parika.model.Subscription;
import com.stadium_parika.model.User;
import com.stadium_parika.model.VehicleType;
import com.stadium_parika.repository.ParkingRepository;
import com.stadium_parika.repository.ParkingSessionRepository;
import com.stadium_parika.repository.ParkingTarifRepository;
import com.stadium_parika.repository.SubscriptionRepository;
import com.stadium_parika.repository.UserRepository;
import com.stadium_parika.repository.VehicleTypeRepository;
import com.stadium_parika.services.ParkingSessionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingSessionServiceImpl implements ParkingSessionService {
	
	private final ParkingSessionRepository parkingSessionRepository;
	private final ParkingRepository parkingRepository;
	private final VehicleTypeRepository vehicleTypeRepository;
	private final ParkingTarifRepository parkingTarifRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public ParkingSessionServiceImpl(ParkingSessionRepository parkingSessionRepository, ParkingRepository parkingRepository, VehicleTypeRepository vehicleTypeRepository, ParkingTarifRepository parkingTarifRepository, SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
		this.parkingSessionRepository = parkingSessionRepository;
		this.parkingRepository = parkingRepository;
		this.vehicleTypeRepository = vehicleTypeRepository;
		this.parkingTarifRepository = parkingTarifRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.userRepository = userRepository;
	}

	@Override
	public ParkingSessionDto entryParking(ParkingSessionRequestDto dto) {
		
		if (dto.getPlateNumber() == null || dto.getPlateNumber().trim().isEmpty()) {
			throw new InvalidEntityException("Plate number obligatoire", ErrorCodes.PARKING_SESSION_NOT_VALID);
		}
		
		// Vérifier parking
	    Parking parking = parkingRepository.findById(dto.getParkingId())
	            .orElseThrow(() -> new EntityNotFoundException("Parking introuvable"));

	    // Vérifier vehicle type
	    VehicleType vehicleType = vehicleTypeRepository.findById(dto.getVehicleTypeId())
	            .orElseThrow(() -> new EntityNotFoundException("Type véhicule introuvable"));
	    
	    // Vérifier user
	    User user = userRepository.findById(dto.getCreatedById())
	    		.orElseThrow(() -> new EntityNotFoundException("Agent introuvable"));
	    
	    long activeSessions = parkingSessionRepository.countByParkingIdAndStatus(dto.getParkingId(), ParkingSessionStatusEnum.ONGOING);
	    
	    if (activeSessions >= parking.getCapacity()) {
	        throw new InvalidEntityException(
	                "Parking plein",
	                ErrorCodes.PARKING_FULL
	        );
	    }
	    
	    String plate = dto.getPlateNumber().trim().toUpperCase();
	    
	    // Vérifier session active existante
	    Optional<ParkingSession> existing = parkingSessionRepository.findActiveSessionByPlateNumber(plate);
	    
	    if(existing.isPresent()) {
	    	throw new InvalidEntityException("Ce véhicule est déjà dans le parking", ErrorCodes.PARKING_SESSION_ALREADY_EXISTS);
	    }
	    
	    // Création session
	    ParkingSession session = new ParkingSession();
	    session.setParking(parking);
	    session.setVehicleType(vehicleType);
	    session.setPlateNumber(plate);
	    session.setEntryTime(LocalDateTime.now());
	    session.setStatus(ParkingSessionStatusEnum.ONGOING);
	    session.setPaymentStatus(PaymentStatusEnum.UNPAID);
	    session.setCreatedBy(user);
	    session.setCreatedAt(LocalDateTime.now());
	    
	    ParkingSession saved = parkingSessionRepository.save(session);
	    
		return ParkingSessionDto.fromEntity(saved);
	}
	
	@Transactional
	@Override
	public ParkingSessionDto exitParking(Long parkingSessionId) {
		
		ParkingSession session = parkingSessionRepository.findById(parkingSessionId)
				.orElseThrow(() -> new EntityNotFoundException("Session introuvable"));
		
		if (session.getStatus() != ParkingSessionStatusEnum.ONGOING) {
			throw new InvalidEntityException("Session déjà terminée", ErrorCodes.PARKING_SESSION_ALREADY_CLOSED);
		}
		
		// sortie
		session.setExitTime(LocalDateTime.now());
		
		// durée
		long duration = Duration.between(session.getEntryTime(), session.getExitTime()).toMinutes();
		
		if (duration < 0) {
			throw new IllegalStateException("Durée invalide");
		}
		session.setDurationMinutes(Math.toIntExact(duration));
		
		Optional<Subscription> subscription = getValidSubscription(session);
		
		if(subscription.isPresent()) {
			session.setPaymentStatus(PaymentStatusEnum.SUBSCRIPTION);
			session.setCalculatedAmount(BigDecimal.ZERO);
		} else {
			// Calcul tarif
			BigDecimal amount = calculatePrice(
					session.getParking().getId(),
					session.getVehicleType().getId(),
					duration
			);
			session.setCalculatedAmount(amount);
			session.setPaymentStatus(PaymentStatusEnum.UNPAID);
		}
		
		// statut final 
		session.setStatus(ParkingSessionStatusEnum.COMPLETED);
		
		ParkingSession saved = parkingSessionRepository.save(session);
		
		return ParkingSessionDto.fromEntity(saved);
	}
	
	private BigDecimal calculatePrice(Long parkingId, Long vehicleTypeId, long duration) {
		
		int durationInt = Math.toIntExact(duration);
		
		List<ParkingTarif> tarifs = parkingTarifRepository.findTarifsForDuration(parkingId, vehicleTypeId, durationInt);
		
		if(tarifs.isEmpty()) {
			throw new EntityNotFoundException("Aucun tarif trouvé");
		}
		
		// prendre le meilleur match
		ParkingTarif tarif = tarifs.get(0);

	    return tarif.getPrice();
	}
	
	private Optional<Subscription> getValidSubscription(ParkingSession session) {

	    return subscriptionRepository.findValidSubscription(
	            session.getPlateNumber(),
	            session.getParking().getId(),
	            session.getVehicleType().getId(),
	            LocalDate.now()
	    );
	}

	@Override
	public ParkingSessionDto findById(Long id) {
		
		if (id == null) {
			log.error("Parking Session ID is null");
		}
		
		return parkingSessionRepository.findById(id)
				.map(ParkingSessionDto::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException(
						"Aucune session avec l'id" +id+ "n'a été trouvé dans la BDD", 
						ErrorCodes.PARKING_SESSION_NOT_FOUND)
				);
	}

	@Override
	public List<ParkingSessionDto> findAll() {
		
		return parkingSessionRepository.findAll().stream()
				.map(ParkingSessionDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
			log.error("Parking Session ID is null");
		}
		
		parkingSessionRepository.deleteById(id);
	}
	
}
