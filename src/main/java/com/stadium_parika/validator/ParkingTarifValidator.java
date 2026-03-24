package com.stadium_parika.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.stadium_parika.dto.ParkingTarifRequestDto;

public class ParkingTarifValidator {
	
	public static List<String> validate(ParkingTarifRequestDto dto){
		List<String> errors = new ArrayList<>();
		
		if(dto == null) {
			errors.add("Veuillez selectionner un parking");
			errors.add("Veuillez selectionner un type de vehicule");
			errors.add("Veuillez renseigner une minute minimale");
			errors.add("Veuillez renseigner une minute maximale");
			errors.add("Veuillez renseigner le prix");
			
			return errors;
		}
		
		if(dto.getParkingId() == null || dto.getParkingId() <= 0) {
			errors.add("Veuillez selectionner un parking");
		}
		if(dto.getVehicleTypeId() == null || dto.getVehicleTypeId() <= 0) {
			errors.add("Veuillez selectionner un type de vehicule");
		}
		if(dto.getMinMinutes() == null || dto.getMinMinutes() < 0) {
			errors.add("La minute minimale doit etre supérieur à zéro");
		}
		if(dto.getMaxMinutes() == null || dto.getMaxMinutes() <= 0) {
			errors.add("La minute maximale doit etre supérieu à zéro");
		}
		if(dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.valueOf(0)) == 0) {
			errors.add("Le prix doit etre superieur à zéro");
		}
		
		// Vérifier la cohérence entre min et max
		if(dto.getMinMinutes() != null && dto.getMaxMinutes() != null) {
			
			if(dto.getMaxMinutes() <= dto.getMinMinutes()) {
				errors.add("La minute maximale doit etre supérieur à la minute minimale");
			}
		}
		
		return errors;
	}
}
