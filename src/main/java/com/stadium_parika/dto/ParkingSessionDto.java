package com.stadium_parika.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.stadium_parika.model.ParkingSession;
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
public class ParkingSessionDto {
	
	private Long id;
	private ParkingDto parking;
	private ParkingSlotDto parkingSlot;
	private VehicleTypeDto vehicleType;
	private String plateNumber;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	private Integer durationMinutes;
	private BigDecimal calculatedAmount;
	private PaymentStatusEnum paymentStatus;
	private ParkingSessionStatusEnum status;
	private  UserDto createdBy;
	private LocalDateTime createdAt;
	
	public static ParkingSessionDto fromEntity(ParkingSession parkingSession) {
		if (parkingSession == null) {
			return null;
		}
		
		return ParkingSessionDto.builder()
				.id(parkingSession.getId())
				.parking(ParkingDto.fromEntity(parkingSession.getParking()))
				.parkingSlot(ParkingSlotDto.fromEntity(parkingSession.getParkingSlot()))
				.vehicleType(VehicleTypeDto.fromEntity(parkingSession.getVehicleType()))
				.plateNumber(parkingSession.getPlateNumber())
				.entryTime(parkingSession.getEntryTime())
				.exitTime(parkingSession.getExitTime())
				.durationMinutes(parkingSession.getDurationMinutes())
				.calculatedAmount(parkingSession.getCalculatedAmount())
				.paymentStatus(parkingSession.getPaymentStatus())
				.status(parkingSession.getStatus())
				.createdBy(UserDto.fromEntity(parkingSession.getCreatedBy()))
				.createdAt(parkingSession.getCreatedAt())
				.build();
	}
	
	public static ParkingSession toEntity(ParkingSessionDto parkingSessionDto) {
		if (parkingSessionDto == null) {
			return null;
		}
		
		ParkingSession sessions = new ParkingSession();
		sessions.setId(parkingSessionDto.getId());
		sessions.setParking(ParkingDto.toEntity(parkingSessionDto.getParking()));
		sessions.setParkingSlot(ParkingSlotDto.toEntity(parkingSessionDto.getParkingSlot()));
		sessions.setVehicleType(VehicleTypeDto.toEntity(parkingSessionDto.getVehicleType()));
		sessions.setPlateNumber(parkingSessionDto.getPlateNumber());
		sessions.setEntryTime(parkingSessionDto.getEntryTime());
		sessions.setExitTime(parkingSessionDto.getExitTime());
		sessions.setDurationMinutes(parkingSessionDto.getDurationMinutes());
		sessions.setCalculatedAmount(parkingSessionDto.getCalculatedAmount());
		sessions.setPaymentStatus(parkingSessionDto.getPaymentStatus());
		sessions.setStatus(parkingSessionDto.getStatus());
		sessions.setCreatedBy(UserDto.toEntity(parkingSessionDto.getCreatedBy()));
		sessions.setCreatedAt(parkingSessionDto.getCreatedAt());
		
		return sessions;
	}
}
