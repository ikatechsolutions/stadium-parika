package com.stadium_parika.dto;

import java.math.BigDecimal;

import com.stadium_parika.model.ParkingTarif;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingTarifRequestDto {
	
	private Long id;
	private Long parkingId;
	private Long vehicleTypeId;
	private Integer minMinutes;
	private Integer maxMinutes;
	private BigDecimal price;
	
}
