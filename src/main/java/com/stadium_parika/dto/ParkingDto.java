package com.stadium_parika.dto;

import java.time.LocalDate;

import com.stadium_parika.model.Parking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingDto {
	
	private Long id;
	private String name;
	private String location;
	private Integer capacity;
	private Boolean has_slots;
	private Boolean is_active;
	private LocalDate creationDate;
	private LocalDate updateDate;
	
	public static ParkingDto fromEntity(Parking parking) {
		if (parking == null) {
			return null;
		}
		
		return ParkingDto.builder()
				.id(parking.getId())
				.name(parking.getName())
				.location(parking.getLocation())
				.capacity(parking.getCapacity())
				.has_slots(parking.getHas_slots())
				.is_active(parking.getIs_active())
				.creationDate(parking.getCreationDate())
				.updateDate(parking.getUpdatedDate())
				.build();
	}
	
	public static Parking toEntity(ParkingDto parkingDto) {
		if (parkingDto == null) {
			return null;
		}
		
		Parking parkings = new Parking();
		parkings.setId(parkingDto.getId());
		parkings.setName(parkingDto.getName());
		parkings.setLocation(parkingDto.getLocation());
		parkings.setCapacity(parkingDto.getCapacity());
		parkings.setHas_slots(parkingDto.getHas_slots());
		parkings.setIs_active(parkingDto.getIs_active());
		parkings.setCreationDate(parkingDto.getCreationDate());
		parkings.setUpdatedDate(parkingDto.getUpdateDate());
		
		return parkings;
	}
}
