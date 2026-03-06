package com.stadium_parika.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscription_plans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlan extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "parking_id", nullable = false)
	private Parking parking;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_type_id", nullable = false)
	private VehicleType vehicleType;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "duration_days", nullable = false)
	private Integer durationDays;
	
	@Column(name = "price", nullable = false)
	private BigDecimal price;
	
	@Column(name = "is_active")
	private Boolean isActive = true;
	
	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;
}
