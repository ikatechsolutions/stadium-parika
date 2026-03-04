package com.stadium_parika.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.stadium_parika.dto.VehicleTypeDto;

public class VehicleTypeValidator {
	
	public static List<String> validate(VehicleTypeDto vehicleTypeDto){
		List<String> errors = new ArrayList<>();
		
		if(vehicleTypeDto == null || !StringUtils.hasLength(vehicleTypeDto.getName())) {
			errors.add("Veuillez renseigner un nom du type de vehicule");
		}
		
		return errors;
	}
}
