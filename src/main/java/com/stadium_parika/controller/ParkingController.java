package com.stadium_parika.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.stadium_parika.controller.api.ParkingApi;
import com.stadium_parika.dto.ParkingDto;
import com.stadium_parika.services.ParkingService;

@RestController
public class ParkingController implements ParkingApi {
	
	private final ParkingService parkingService;
	
	@Autowired
	public ParkingController(ParkingService parkingService) {
		this.parkingService = parkingService;
	}
	
	@Override
	public ParkingDto save(ParkingDto dto) {
		
		return parkingService.save(dto);
	}

	@Override
	public ParkingDto findById(Long id) {
		
		return parkingService.findById(id);
	}

	@Override
	public Page<ParkingDto> findByParkingName(String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return parkingService.findParkingByName(search, pageable);
	}

	@Override
	public List<ParkingDto> findAll() {
		
		return parkingService.findAll();
	}

	@Override
	public void delete(Long id) {
		
		parkingService.delete(id);
		
	}

	@Override
	public ParkingDto activateParking(Long id) {
		
		return parkingService.activateParking(id);
	}

	@Override
	public ParkingDto deactivateParking(Long id) {
		
		return parkingService.deactivateParking(id);
	}

}
