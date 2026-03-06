package com.stadium_parika.dto;

import java.time.LocalDate;

import com.stadium_parika.model.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeDto {
	
	private Long id;
	private String name;
	private String description;
	private String photo;
	private Boolean isActive;
	private LocalDate creationDate;
	
	public static VehicleTypeDto fromEntity(VehicleType vehicleType) {
		if (vehicleType == null) {
			return null;
		}
		
		return VehicleTypeDto.builder()
				.id(vehicleType.getId())
				.name(vehicleType.getName())
				.description(vehicleType.getDescription())
				.photo(vehicleType.getPhoto())
				.isActive(vehicleType.getIsActive())
				.creationDate(vehicleType.getCreationDate())
				.build();
	}
	
	public static VehicleType toEntity(VehicleTypeDto vehicleTypeDto) {
		if (vehicleTypeDto == null) {
			return null;
		}
		
		VehicleType vehicleTypes = new VehicleType();
		vehicleTypes.setId(vehicleTypeDto.getId());
		vehicleTypes.setName(vehicleTypeDto.getName());
		vehicleTypes.setDescription(vehicleTypeDto.getDescription());
		vehicleTypes.setPhoto(vehicleTypeDto.getPhoto());
		vehicleTypes.setIsActive(vehicleTypeDto.getIsActive());
		vehicleTypes.setCreationDate(vehicleTypeDto.getCreationDate());
		
		return vehicleTypes;
	}
}
