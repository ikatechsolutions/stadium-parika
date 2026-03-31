package com.stadium_parika.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stadium_parika.dto.ParkingDto;
import com.stadium_parika.exceptions.EntityNotFoundException;
import com.stadium_parika.exceptions.ErrorCodes;
import com.stadium_parika.exceptions.InvalidEntityException;
import com.stadium_parika.exceptions.InvalidOperationException;
import com.stadium_parika.model.Parking;
import com.stadium_parika.model.ParkingSlot;
import com.stadium_parika.model.ParkingSlotStatusEnum;
import com.stadium_parika.repository.ParkingRepository;
import com.stadium_parika.repository.ParkingSessionRepository;
import com.stadium_parika.repository.ParkingSlotRepository;
import com.stadium_parika.services.ParkingService;
import com.stadium_parika.validator.ParkingValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingServiceImpl implements ParkingService {
	
	private final ParkingRepository parkingRepository;
	private final ParkingSlotRepository parkingSlotRepository;
	private final ParkingSessionRepository parkingSessionRepository;
	
	@Autowired
	public ParkingServiceImpl(ParkingRepository parkingRepository, ParkingSlotRepository parkingSlotRepository, ParkingSessionRepository parkingSessionRepository) {
		this.parkingRepository = parkingRepository;
		this.parkingSlotRepository = parkingSlotRepository;
		this.parkingSessionRepository = parkingSessionRepository;
	}
	
//	@Override
//	public ParkingDto save(ParkingDto dto) {
//		
//		List<String> errors = ParkingValidator.validate(dto);
//		if (!errors.isEmpty()) {
//			log.error("Parking is not valid {}", dto);
//			throw new InvalidEntityException("Le parking n'est pas valide", ErrorCodes.PARKING_NOT_VALID, errors);
//		}
//		if ((dto.getId() == null || dto.getId().compareTo(0L) == 0)) {
//			
//			if (parkingAlreadyExists(dto.getName())) {
//				throw new InvalidEntityException("Une autre parking avec le meme nom existe deja", ErrorCodes.PARKING_ALREADY_EXISTS, 
//						Collections.singletonList("Une autre parking avec le meme nom existe deja dans la BDD"));
//			}
//			dto.setIs_active(true);
//			dto.setCreationDate(LocalDate.now());
//			dto.setUpdateDate(LocalDate.now());
//			return ParkingDto.fromEntity(parkingRepository.save(ParkingDto.toEntity(dto)));
//		}
//		
//		Parking existingParking = parkingRepository.findParkingById(dto.getId());
//		if (existingParking != null || !existingParking.getName().equals(dto.getName())) {
//			
//			if(parkingAlreadyExists(dto.getName())) {
//				throw new InvalidEntityException("Une autre parking avec le meme nom existe deja", ErrorCodes.PARKING_ALREADY_EXISTS, 
//						Collections.singletonList("Une autre parking avec le meme nom existe deja dans la BDD"));
//			}
//			dto.setUpdateDate(LocalDate.now());
//		}
//		
//		return ParkingDto.fromEntity(parkingRepository.save(ParkingDto.toEntity(dto)));
//	}
	
	@Override
	public ParkingDto save(ParkingDto dto) {

	    List<String> errors = ParkingValidator.validate(dto);

	    if (!errors.isEmpty()) {
	        log.error("Parking is not valid {}", dto);
	        throw new InvalidEntityException(
	                "Le parking n'est pas valide",
	                ErrorCodes.PARKING_NOT_VALID,
	                errors
	        );
	    }

	    Parking savedParking;

	    // =========================
	    // CREATION
	    // =========================
	    if (dto.getId() == null || dto.getId().compareTo(0L) == 0) {

	        if (parkingAlreadyExists(dto.getName())) {
	            throw new InvalidEntityException(
	                    "Une autre parking avec le meme nom existe deja",
	                    ErrorCodes.PARKING_ALREADY_EXISTS,
	                    Collections.singletonList("Une autre parking avec le meme nom existe deja dans la BDD")
	            );
	        }

	        dto.setIs_active(true);
	        dto.setCreationDate(LocalDate.now());
	        dto.setUpdateDate(LocalDate.now());

	        savedParking = parkingRepository.save(ParkingDto.toEntity(dto));

	        // =========================
	        // CREATION DES SLOTS
	        // =========================
	        if (Boolean.TRUE.equals(savedParking.getHas_slots())) {

	            List<ParkingSlot> slots = generateSmartSlots(savedParking);

	            parkingSlotRepository.saveAll(slots);
	        }

	        return ParkingDto.fromEntity(savedParking);
	    }

	    // =========================
	    // UPDATE
	    // =========================
	    Parking existingParking = parkingRepository.findParkingById(dto.getId());

	    if (existingParking != null && !existingParking.getName().equals(dto.getName())) {

	        if (parkingAlreadyExists(dto.getName())) {
	            throw new InvalidEntityException(
	                    "Une autre parking avec le meme nom existe deja",
	                    ErrorCodes.PARKING_ALREADY_EXISTS,
	                    Collections.singletonList("Une autre parking avec le meme nom existe deja dans la BDD")
	            );
	        }
	    }

	    dto.setUpdateDate(LocalDate.now());

	    savedParking = parkingRepository.save(ParkingDto.toEntity(dto));

	    return ParkingDto.fromEntity(savedParking);
	}

	private List<ParkingSlot> generateSmartSlots(Parking parking) {
		
		List<ParkingSlot> slots = new ArrayList<>();

	    int capacity = parking.getCapacity();

	    int slotsPerRow = 10; // nombre de places par ligne

	    int row = 0;
	    int column = 1;

	    for (int i = 1; i <= capacity; i++) {

	        char rowLetter = (char) ('A' + row);

	        String slotCode = rowLetter + String.valueOf(column);

	        ParkingSlot slot = new ParkingSlot();
	        slot.setParking(parking);
	        slot.setSlotCode(slotCode);;
	        slot.setStatus(ParkingSlotStatusEnum.AVAILABLE);
	        slot.setCreationDate(LocalDate.now());

	        slots.add(slot);

	        column++;

	        if (column > slotsPerRow) {
	            column = 1;
	            row++;
	        }
	    }

	    return slots;
	}

	private boolean parkingAlreadyExists(String name) {
		Optional<Parking> parking = parkingRepository.findParkingByName(name);
		return parking.isPresent();
	}

	@Override
	public ParkingDto findById(Long id) {
		if (id == null) {
			log.error("Parking is null");
		}
		
		return parkingRepository.findById(id)
				.map(ParkingDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun parking avec l'ID = " +id+ " n'a ete trouve dans la BDD", 
						ErrorCodes.PARKING_NOT_VALID)
						);
	}

	@Override
	public Page<ParkingDto> findParkingByName(String search, Pageable pageable) {
		Page<Parking> parkings;
		if (search != null) {
			parkings = parkingRepository.findByParkingNameLike(search, pageable);
		} else {
			parkings = parkingRepository.findAllParking(pageable);
		}
		
		return parkings.map(ParkingDto::fromEntity);
	}

	@Override
	public List<ParkingDto> findAll() {
		
		return parkingRepository.findAll().stream()
				.map(ParkingDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
	        log.error("Parking ID is null");
	        return;
	    }

	    Parking parking = parkingRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException(
	                    "Aucun parking trouvé avec l'id " + id,
	                    ErrorCodes.PARKING_NOT_FOUND
	            ));

	    // vérifier les sessions
	    boolean hasSessions = parkingSessionRepository.existsByParkingId(id);

	    if (hasSessions) {
	        throw new InvalidOperationException(
	                "Impossible de supprimer ce parking car il possède des sessions historiques",
	                ErrorCodes.PARKING_IN_USE
	        );
	    }

	    parkingRepository.deleteById(id);
		
	}

	@Override
	public ParkingDto activateParking(long id) {
		
		Parking parking = parkingRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException(
	                    "Aucun parking trouvé avec l'id " + id,
	                    ErrorCodes.PARKING_NOT_FOUND
	            ));

	    parking.setIs_active(true);
	    parking.setUpdatedDate(LocalDate.now());

	    return ParkingDto.fromEntity(parkingRepository.save(parking));
	}

	@Override
	public ParkingDto deactivateParking(Long id) {
		
		Parking parking = parkingRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException(
	                    "Aucun parking trouvé avec l'id " + id,
	                    ErrorCodes.PARKING_NOT_FOUND
	            ));

	    parking.setIs_active(false);
	    parking.setUpdatedDate(LocalDate.now());

	    return ParkingDto.fromEntity(parkingRepository.save(parking));
	}

}
