package com.stadium_parika.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.stadium_parika.model.Parking;
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
	private Parking parking;
	private VehicleType vehicleType;
	private Integer minMinutes;
	private Integer maxMinutes;
	private BigDecimal price;
	private Boolean is_active;
	private LocalDate createdDate;
	
	
}
