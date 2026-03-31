package com.stadium_parika.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stadium_parika.dto.ParkingSessionDto;
import com.stadium_parika.dto.ParkingSessionRequestDto;

public interface ParkingSessionService {
	
	ParkingSessionDto entryParking(ParkingSessionRequestDto dto);
	
	ParkingSessionDto exitParking(Long parkingSessionId);
	
	ParkingSessionDto findById(Long id);
	
	List<ParkingSessionDto> findAll();
	
//	Page<ParkingSessionDto> findAllParkingSession(Pageable pageable);
	
//	Page<ParkingSessionDto> findActiveParkingSessionByParkingId(Long parkingId, String search, Pageable pageable);
//	
//	ParkingSessionDto getActiveParkingSessionByParkingIdAndPlateNumber(Long parkingId, String plateNumber);
//	
//	Page<ParkingSessionDto> getVehiclesInParking(Long parkingId, Long agentId, String search, Pageable pageable);
	
	void delete(Long id);
}
