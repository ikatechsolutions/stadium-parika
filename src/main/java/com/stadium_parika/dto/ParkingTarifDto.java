package com.stadium_parika.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.stadium_parika.model.Parking;
import com.stadium_parika.model.ParkingTarif;
import com.stadium_parika.model.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingTarifDto {
	
	private Long id;
	private ParkingDto parking;
	private VehicleTypeDto vehicleType;
	private Integer minMinutes;
	private Integer maxMinutes;
	private BigDecimal price;
	private Boolean is_active;
	private LocalDate createdDate;
	
	public static ParkingTarifDto fromEntity(ParkingTarif parkingTarif) {
		if (parkingTarif == null) {
			return null;
		}
		
		return ParkingTarifDto.builder()
				.id(parkingTarif.getId())
				.parking(ParkingDto.fromEntity(parkingTarif.getParking()))
				.vehicleType(VehicleTypeDto.fromEntity(parkingTarif.getVehicleType()))
				.minMinutes(parkingTarif.getMinMinutes())
				.maxMinutes(parkingTarif.getMaxMinutes())
				.price(parkingTarif.getPrice())
				.is_active(parkingTarif.getIs_active())
				.createdDate(parkingTarif.getCreatedDate())
				.build();
				
	}
	
//	public static ParkingTarif toEntity(ParkingTarifDto parkingTarifDto) {
//		if (parkingTarifDto == null) {
//			return null;
//		}
//		
//		ParkingTarif parkingTarifs = new ParkingTarif();
//		parkingTarifs.setId(parkingTarifDto.getId());
//		parkingTarifs.setParking(ParkingDto.toEntity(parkingTarifDto.getParking()));
//		parkingTarifs.setVehicleType(VehicleTypeDto.toEntity(parkingTarifDto.getVehicleType()));
//		parkingTarifs.setPrice(parkingTarifDto.getPrice());
//		parkingTarifs.setIs_active(parkingTarifDto.getIs_active());
//		parkingTarifs.setMin_minutes(parkingTarifDto.getMinMinutes());
//		parkingTarifs.setMax_minutes(parkingTarifDto.getMaxMinutes());
//		parkingTarifs.setCreatedDate(parkingTarifDto.getCreatedDate());
//		
//		return parkingTarifs;
//	}
}
