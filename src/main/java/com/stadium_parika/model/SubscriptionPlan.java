package com.stadium_parika.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscription_plans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlan extends AbstractEntity {
	
	@NotNull(message = "Le parking est obligatoire")
	@ManyToOne
	@JoinColumn(name = "parking_id", nullable = false)
	private Parking parking;
	
	@NotNull(message = "Le type de véhicule est obligatoire")
	@ManyToOne
	@JoinColumn(name = "vehicle_type_id", nullable = false)
	private VehicleType vehicleType;
	
	@NotBlank(message = "Le nom est obligatoire")
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull(message = "La durée des jours est obligatoire")
	@Min(value = 1, message = "La durée doit être au moins 1 jour")
	@Column(name = "duration_days", nullable = false)
	private Integer durationDays;
	
	@NotNull(message = "Le prix est obligatoire")
	@DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
	@Column(name = "price", nullable = false)
	private BigDecimal price;
	
	@Column(name = "is_active")
	private Boolean isActive = true;
	
	@PastOrPresent(message = "Date invalide")
	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;
	
	@PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }
}
