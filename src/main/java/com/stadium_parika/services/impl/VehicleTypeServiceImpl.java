package com.stadium_parika.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stadium_parika.dto.VehicleTypeDto;
import com.stadium_parika.exceptions.EntityNotFoundException;
import com.stadium_parika.exceptions.ErrorCodes;
import com.stadium_parika.exceptions.InvalidEntityException;
import com.stadium_parika.model.VehicleType;
import com.stadium_parika.repository.VehicleTypeRepository;
import com.stadium_parika.services.VehicleTypeService;
import com.stadium_parika.validator.VehicleTypeValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleTypeServiceImpl implements VehicleTypeService {
	
	private final VehicleTypeRepository vehicleTypeRepository;
	
	@Autowired
	public VehicleTypeServiceImpl(VehicleTypeRepository vehicleTypeRepository) {
		this.vehicleTypeRepository = vehicleTypeRepository;
	}

	@Override
	public VehicleTypeDto save(VehicleTypeDto dto) {
		List<String> errors = VehicleTypeValidator.validate(dto);
		if (!errors.isEmpty()) {
			log.error("Vehicle type is not valid {}", dto);
			throw new InvalidEntityException("Le type vehicule n'est pas valide", ErrorCodes.VEHICLETYPE_NOT_VALID, errors);
		}
		if ((dto.getId() ==null || dto.getId().compareTo(0L) == 0)){

			if(vehicleTypeAlreadyExists(dto.getName())) {
				throw new InvalidEntityException("Un autre type de vehicule avec le meme nom existe deja", ErrorCodes.VEHICLETYPE_ALREADY_EXISTS,
						Collections.singletonList("Un autre type de vehicule avec le meme nom existe deja dans la BDD"));
			}
			return VehicleTypeDto.fromEntity(
					vehicleTypeRepository.save(VehicleTypeDto.toEntity(dto))
			);
		}

		VehicleType existingVehicleType=vehicleTypeRepository.findVehicleTypeById(dto.getId());
		if (existingVehicleType !=null && !existingVehicleType.getName().equals(dto.getName())){

			if(vehicleTypeAlreadyExists(dto.getName())) {
				throw new InvalidEntityException("Un autre type de vehicule avec le meme nom existe deja", ErrorCodes.VEHICLETYPE_ALREADY_EXISTS,
						Collections.singletonList("Un autre type de vehicule avec le meme nom existe deja dans la BDD"));
			}
		}

		return VehicleTypeDto.fromEntity(
				vehicleTypeRepository.save(VehicleTypeDto.toEntity(dto))
		);
	}
	
	private boolean vehicleTypeAlreadyExists(String name) {
		Optional<VehicleType> vehicleType = vehicleTypeRepository.findVehicleTypeByName(name);
		return vehicleType.isPresent();
	}

	@Override
	public VehicleTypeDto findById(Long id) {
		if(id == null) {
			log.error("Vehicle type is null");
		}
		return vehicleTypeRepository.findById(id)
				.map(VehicleTypeDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun type de vehicle avec l'ID = " +id+ " n' a ete trouve dans la BDD ",
						ErrorCodes.VEHICLETYPE_NOT_FOUND)
						);
	}

	@Override
	public Page<VehicleTypeDto> findByVehicleTypeName(String search, Pageable pageable) {
		Page<VehicleType> vehicleTypes;
		if (search != null) {
			vehicleTypes = vehicleTypeRepository.findByVehiculeTypeNameLike(search, pageable);
		} else {
			// If no category is provided, fetch all products
			vehicleTypes = vehicleTypeRepository.findAllVehiculeType(pageable);
		}

		return vehicleTypes.map(VehicleTypeDto::fromEntity);
	}

	@Override
	public List<VehicleTypeDto> findAll() {
		return vehicleTypeRepository.findAll().stream()
				.map(VehicleTypeDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		// On ne supprime pas mais plutot on désactive
		VehicleType vehicleType = vehicleTypeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Type de vehicule introuvable"));
		
		vehicleType.setIsActive(false);
		
		vehicleTypeRepository.save(vehicleType);
		
	}
}
