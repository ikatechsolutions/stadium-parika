package com.stadium_parika.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.stadium_parika.dto.ParkingSessionDto;

public class ParkingSessionValidator {
	
	public static List<String> validate(ParkingSessionDto dto){
		List<String> errors = new ArrayList<>();
		
		if(dto == null) {
			errors.add("Veuillez selectionner un parking");
			errors.add("Veuillez selectionner un type de vehicule");
			errors.add("Veuillez renseigner un numero de plaque d'immatriculation");
			
			return errors;
		}
		
		if(dto.getParking() == null || dto.getParking().getId() == null || dto.getParking().getId().compareTo(0L) == 0) {
			errors.add("Veuillez selectionner un parking");
		}
		
		if(dto.getVehicleType() == null || dto.getVehicleType().getId() == null || dto.getVehicleType().getId().compareTo(0L) == 0) {
			errors.add("Veuillez selectionner un type de vehicule");
		}
		
		if(dto.getPlateNumber() == null || !StringUtils.hasLength(dto.getPlateNumber())) {
			errors.add("Veuillez renseigner un numero de plaque d'immatriculation");
		}
		
		return errors;
	}
}
