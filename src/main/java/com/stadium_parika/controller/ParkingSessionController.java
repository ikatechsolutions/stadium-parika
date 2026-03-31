package com.stadium_parika.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.stadium_parika.controller.api.ParkingSessionApi;
import com.stadium_parika.dto.ParkingSessionDto;
import com.stadium_parika.dto.ParkingSessionRequestDto;
import com.stadium_parika.services.ParkingSessionService;

@RestController
public class ParkingSessionController implements ParkingSessionApi {
	
	private final ParkingSessionService parkingSessionService;
	
	@Autowired
	public ParkingSessionController(ParkingSessionService parkingSessionService) {
		this.parkingSessionService = parkingSessionService;
	}

	@Override
	public ParkingSessionDto entryParking(ParkingSessionRequestDto dto) {
		
		return parkingSessionService.entryParking(dto);
	}

	@Override
	public ParkingSessionDto exitParking(Long parkingSessionId) {
		
		return parkingSessionService.exitParking(parkingSessionId);
	}

	@Override
	public ParkingSessionDto findById(Long id) {
		
		return parkingSessionService.findById(id);
	}

	@Override
	public List<ParkingSessionDto> findAll() {
		
		return parkingSessionService.findAll();
	}

	@Override
	public void delete(Long id) {
		parkingSessionService.delete(id);
		
	}
}
