package com.stadium_parika.services.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stadium_parika.dto.ParkingTarifDto;
import com.stadium_parika.dto.ParkingTarifRequestDto;
import com.stadium_parika.exceptions.ErrorCodes;
import com.stadium_parika.exceptions.InvalidEntityException;
import com.stadium_parika.exceptions.EntityNotFoundException;
import com.stadium_parika.model.Parking;
import com.stadium_parika.model.ParkingTarif;
import com.stadium_parika.model.VehicleType;
import com.stadium_parika.repository.ParkingRepository;
import com.stadium_parika.repository.ParkingTarifRepository;
import com.stadium_parika.repository.VehicleTypeRepository;
import com.stadium_parika.services.ParkingTarifService;
import com.stadium_parika.validator.ParkingTarifValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingTarifServiceImpl implements ParkingTarifService {
	
	private final ParkingTarifRepository parkingTarifRepository;
	private final ParkingRepository parkingRepository;
	private final VehicleTypeRepository vehicleTypeRepository;
	
	@Autowired
	public ParkingTarifServiceImpl(ParkingTarifRepository parkingTarifRepository, ParkingRepository parkingRepository, VehicleTypeRepository vehicleTypeRepository) {
		this.parkingTarifRepository = parkingTarifRepository;
		this.parkingRepository = parkingRepository;
		this.vehicleTypeRepository = vehicleTypeRepository;
	}
	
	@Transactional
	@Override
	public ParkingTarifDto save(ParkingTarifRequestDto dto) {
		
		List<String> errors = ParkingTarifValidator.validate(dto);

	    if (!errors.isEmpty()) {
	        log.error("Parking tarif is not valid {}", dto);
	        throw new InvalidEntityException(
	                "Le tarif n'est pas valide",
	                ErrorCodes.PARKING_TARIF_NOT_VALID,
	                errors
	        );
	    }
	    
	    // Vérifier Parking
	    Parking parking = parkingRepository.findById(dto.getParkingId())
	    		.orElseThrow(() -> new EntityNotFoundException("Parking introuvable"));
	    
	    // Vérifier vehicle type
	    VehicleType vehicleType = vehicleTypeRepository.findById(dto.getVehicleTypeId())
	    		.orElseThrow(() -> new EntityNotFoundException("Type de véhicule introuvable"));
	    
	    ParkingTarif parkingTarif;
	    
	    // Cas UPDATE
	    if (dto.getId() != null) {
	    	parkingTarif = parkingTarifRepository.findById(dto.getId())
	    			.orElseThrow(() -> new EntityNotFoundException("Tarif introuvable"));
	    } else {
	    	// Cas CREATE
	    	parkingTarif = new ParkingTarif();
	    	parkingTarif.setCreatedDate(LocalDate.now());
	    }
	    
	    // Vérifier le chevauchement
	    List<ParkingTarif> overlapping = parkingTarifRepository.findOverlappingTarifs(
	    		dto.getParkingId(), 
	    		dto.getVehicleTypeId(), 
	    		dto.getMinMinutes(), 
	    		dto.getMaxMinutes()
	    );
	    
	    // IMPORTANT : exclure lui-même en update
	    if (dto.getId() != null) {
	    	overlapping = overlapping.stream()
	    			.filter(t -> !t.getId().equals(dto.getId()))
	    			.collect(Collectors.toList());
	    }
	    
	    if (!overlapping.isEmpty()) {
	    	throw new InvalidEntityException(
	    			"Intervalle de tarif invalide",
	    			ErrorCodes.PARKING_TARIF_NOT_VALID,
	    			Collections.singletonList("Les intervalles de temps se chevauchent")
	    	);
	    }
	    
	    // Mise à jour des champs
	    parkingTarif.setParking(parking);
	    parkingTarif.setVehicleType(vehicleType);
	    parkingTarif.setMinMinutes(dto.getMinMinutes());
	    parkingTarif.setMaxMinutes(dto.getMaxMinutes());
	    parkingTarif.setPrice(dto.getPrice());
	    parkingTarif.setIs_active(true);
	    
	    ParkingTarif saved = parkingTarifRepository.save(parkingTarif);
	    
	    // Désactiver anciens tarifs actifs
	    parkingTarifRepository.deactivateExistingTarifs(
	    		dto.getParkingId(), 
	    		dto.getVehicleTypeId(), 
	    		dto.getMinMinutes(), 
	    		dto.getMaxMinutes(), 
	    		saved.getId()
	    );
	    
	    return ParkingTarifDto.fromEntity(saved);
	}

	@Override
	public ParkingTarifDto findById(Long id) {
		if (id == null) {
			log.error("Parking is null");
		}
		
		return parkingTarifRepository.findById(id)
				.map(ParkingTarifDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun parking avec l'ID = " +id+ " n'a ete trouve dans la BDD", 
						ErrorCodes.PARKING_TARIF_NOT_VALID)
						);
	}

	@Override
	public List<ParkingTarifDto> findAll() {
		
		return parkingTarifRepository.findAll().stream()
				.map(ParkingTarifDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<ParkingTarifDto> findByNameLike(Long idParking, String search, Pageable pageable) {
		
		Page<ParkingTarif> parkingTarifs;
		if (search != null) {
			parkingTarifs = parkingTarifRepository.findByNameLike(idParking, search, pageable);
		} else {
			parkingTarifs = parkingTarifRepository.findParkingTarifs(idParking, pageable);
		}
		return parkingTarifs.map(ParkingTarifDto::fromEntity);
	}

	@Override
	public List<ParkingTarifDto> findByParkingTarif(Long idParking) {
		
		return parkingTarifRepository.findByParkingTarifs(idParking).stream()
				.map(ParkingTarifDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
	        log.error("Tarif ID is null");
	        return;
	    }
		
		parkingTarifRepository.deleteById(id);
	}

}
