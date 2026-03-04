package com.stadium_parika.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stadium_parika.dto.VehicleTypeDto;

public interface VehicleTypeService {
	
	VehicleTypeDto save(VehicleTypeDto dto);
	
	VehicleTypeDto findById(Long id);
	
	Page<VehicleTypeDto> findByVehicleTypeName(String search, Pageable pageable);
	
	List<VehicleTypeDto> findAll();
	
	void delete(Long id);
}
