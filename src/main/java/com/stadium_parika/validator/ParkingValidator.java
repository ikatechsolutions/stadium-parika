package com.stadium_parika.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.stadium_parika.dto.ParkingDto;

public class ParkingValidator {
	
	public static List<String> validate(ParkingDto parkingDto){
		List<String> errors = new ArrayList<>();
		
		if(parkingDto == null) {
			errors.add("Veuillez renseigner un nom du parking");
			errors.add("Veuillez renseigner une localisation du parking");
			errors.add("Veuillez renseigner la capacité du parking");
			
			return errors;
		}
		
		if(!StringUtils.hasLength(parkingDto.getName())) {
			errors.add("Veuillez renseigner un nom du parking");
		}
		if(!StringUtils.hasLength(parkingDto.getLocation())) {
			errors.add("Veuillez renseigner une localisation du parking");
		}
		if(parkingDto.getCapacity() <= 0) {
			errors.add("Veuillez renseigner la capacite du parking");
		}
		
		return errors;
	}
}
