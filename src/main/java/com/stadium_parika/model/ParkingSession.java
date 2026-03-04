package com.stadium_parika.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSession extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "parking_id", nullable = false)
	private Parking parking;
	
	@ManyToOne
	@JoinColumn(name = "parking_slot_id", nullable = true)
	private ParkingSlot parkingSlot;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_type_id", nullable = false)
	private VehicleType vehicleType;
	
	@Column(name = "plate_number", nullable = false)
	private String plateNumber;
	
	@Column(name = "entry_time", nullable = false)
	private LocalDateTime entryTime;
	
	@Column(name = "exit_time", nullable = true)
	private LocalDateTime exitTime;
	
	@Column(name = "duration_minutes")
	private Integer durationMinutes;
	
	@Column(name = "calculated_amount")
	private BigDecimal calculatedAmount;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatusEnum paymentStatus;
	
	@Enumerated(EnumType.STRING)
	private ParkingSessionStatusEnum status;
	
	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
