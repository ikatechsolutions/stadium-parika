package com.stadium_parika.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.stadium_parika.controller.api.ParkingTarifApi;
import com.stadium_parika.dto.ParkingTarifDto;
import com.stadium_parika.dto.ParkingTarifRequestDto;
import com.stadium_parika.services.ParkingTarifService;

@RestController
public class ParkingTarifController implements ParkingTarifApi {
	
	private final ParkingTarifService parkingTarifService;
	
	@Autowired
	public ParkingTarifController(ParkingTarifService parkingTarifService) {
		this.parkingTarifService = parkingTarifService;
	}

	@Override
	public ParkingTarifDto save(ParkingTarifRequestDto dto) {
		
		return parkingTarifService.save(dto);
	}

	@Override
	public ParkingTarifDto findById(Long id) {
		
		return parkingTarifService.findById(id);
	}

	@Override
	public List<ParkingTarifDto> findAll() {
		
		return parkingTarifService.findAll();
	}

	@Override
	public Page<ParkingTarifDto> findParkingTarifs(Long idParking, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return parkingTarifService.findByNameLike(idParking, search, pageable);
	}

	@Override
	public List<ParkingTarifDto> findByParkingTarifs(Long idParking) {
		
		return parkingTarifService.findByParkingTarif(idParking);
	}

	@Override
	public void delete(Long id) {
		
		parkingTarifService.delete(id);
		
	}

} 
