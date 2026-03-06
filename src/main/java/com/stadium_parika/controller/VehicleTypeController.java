package com.stadium_parika.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stadium_parika.controller.api.VehicleTypeApi;
import com.stadium_parika.dto.VehicleTypeDto;
import com.stadium_parika.services.StorageService;
import com.stadium_parika.services.VehicleTypeService;

@RestController
public class VehicleTypeController implements VehicleTypeApi {
	
	private final VehicleTypeService vehicleTypeService;
	
	@Autowired
	public VehicleTypeController(VehicleTypeService vehicleTypeService) {
		this.vehicleTypeService = vehicleTypeService;
	}

	@Override
	public VehicleTypeDto save(String name, String description, MultipartFile photo) {
		
		VehicleTypeDto dto = new VehicleTypeDto();
		dto.setName(name);
		dto.setDescription(description);
		
		return vehicleTypeService.save(dto, photo);
	}

	@Override
	public VehicleTypeDto findById(Long id) {
		
		return vehicleTypeService.findById(id);
	}

	@Override
	public Page<VehicleTypeDto> findByVehiculeTypeName(String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return vehicleTypeService.findByVehicleTypeName(search, pageable);
	}
	
	@Override
	public void delete(Long id) {
		vehicleTypeService.delete(id);
		
	}

	@Override
	public List<VehicleTypeDto> findAll() {
		
		return vehicleTypeService.findAll();
	}
}
