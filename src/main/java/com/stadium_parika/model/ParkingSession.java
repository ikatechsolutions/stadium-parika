package com.stadium_parika.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSession extends AbstractEntity {
	
	@NotNull(message = "Le parking est obligatoire")
	@ManyToOne
	@JoinColumn(name = "parking_id", nullable = false)
	private Parking parking;
	
	@NotNull(message = "Le parking slot est obligatoire")
	@ManyToOne
	@JoinColumn(name = "parking_slot_id", nullable = true)
	private ParkingSlot parkingSlot;
	
	@NotNull(message = "Le type de vehicule est obligatoire")
	@ManyToOne
	@JoinColumn(name = "vehicle_type_id", nullable = false)
	private VehicleType vehicleType;
	
	@NotBlank(message = "Le numéro de plaque est obligatoire")
	@Column(name = "plate_number", nullable = false)
	private String plateNumber;
	
	@PastOrPresent(message = "entry time est invalide")
	@Column(name = "entry_time", nullable = false)
	private LocalDateTime entryTime;
	
	@PastOrPresent(message = "exit time est invalide")
	@Column(name = "exit_time", nullable = true)
	private LocalDateTime exitTime;
	
	@NotNull(message = "La durée est obligatoire")
	@Column(name = "duration_minutes")
	private Integer durationMinutes;
	
	@NotNull(message = "Le montant calculé est obligatoire")
	@Column(name = "calculated_amount")
	private BigDecimal calculatedAmount;
	
	@NotEmpty(message = "Le status de paiement est obligatoire")
	@Enumerated(EnumType.STRING)
	private PaymentStatusEnum paymentStatus;
	
	@NotEmpty(message = "Le status de session est obligatoire")
	@Enumerated(EnumType.STRING)
	private ParkingSessionStatusEnum status;
	
	@NotNull(message = "L'utilisateur qui enregistre le parking est obligatoire")
	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;
	
	@PastOrPresent(message = "Date de création invalide")
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
