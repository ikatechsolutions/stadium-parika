package com.stadium_parika.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.stadium_parika.model.ParkingSessionStatusEnum;
import com.stadium_parika.model.PaymentStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSessionRequestDto {
	
	private Long parkingId;
	private Long vehicleTypeId;
	private String plateNumber;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	private Integer durationMinutes;
	private BigDecimal calculatedAmount;
	private PaymentStatusEnum paymentStatus;
	private ParkingSessionStatusEnum status;
	private  Long createdById;
	private LocalDateTime createdAt;
}
