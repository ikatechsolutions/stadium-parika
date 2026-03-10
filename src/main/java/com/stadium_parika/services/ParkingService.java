package com.stadium_parika.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stadium_parika.dto.ParkingDto;

public interface ParkingService {
	
	ParkingDto save(ParkingDto dto);
	
	ParkingDto findById(Long id);
	
	Page<ParkingDto> findParkingByName(String search, Pageable pageable);
	
	List<ParkingDto> findAll();
	
	ParkingDto activateParking(long id);
	
	ParkingDto deactivateParking(Long id);
	
	void delete(Long id);
	
}
