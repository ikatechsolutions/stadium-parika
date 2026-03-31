package com.stadium_parika.dto;

import java.time.LocalDate;

import com.stadium_parika.model.ParkingSlot;
import com.stadium_parika.model.ParkingSlotStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotDto {
	
	private Long id;
	private ParkingDto parking;
	private String slotCode;
	private ParkingSlotStatusEnum status;
	private LocalDate creationDate;
	
	public static ParkingSlotDto fromEntity(ParkingSlot parkingSlot) {
		if (parkingSlot == null) {
			return null;
		}
		
		return ParkingSlotDto.builder()
				.id(parkingSlot.getId())
				.parking(ParkingDto.fromEntity(parkingSlot.getParking()))
				.slotCode(parkingSlot.getSlotCode())
				.status(parkingSlot.getStatus())
				.creationDate(parkingSlot.getCreationDate())
				.build();
	}
	
	public static ParkingSlot toEntity(ParkingSlotDto parkingSlotDto) {
		if (parkingSlotDto == null) {
			return null;
		}
		
		ParkingSlot slots = new ParkingSlot();
		slots.setId(parkingSlotDto.getId());
		slots.setParking(ParkingDto.toEntity(parkingSlotDto.getParking()));
		slots.setSlotCode(parkingSlotDto.getSlotCode());
		slots.setStatus(parkingSlotDto.getStatus());
		slots.setCreationDate(parkingSlotDto.getCreationDate());
		
		return slots;
	}
}
