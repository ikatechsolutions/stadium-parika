package com.stadium_parika.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stadium_parika.dto.ParkingTarifDto;
import com.stadium_parika.dto.ParkingTarifRequestDto;

public interface ParkingTarifService {
	
	ParkingTarifDto save(ParkingTarifRequestDto dto);

    ParkingTarifDto findById(Long id);

    List<ParkingTarifDto> findAll();

    Page<ParkingTarifDto> findByNameLike(Long idParking, String search, Pageable pageable);
    
    List<ParkingTarifDto> findByParkingTarif(Long idParking);

    void delete(Long id);
}
