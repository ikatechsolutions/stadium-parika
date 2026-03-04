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
@Table(name = "parking_tarifs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingTarif extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "parking_id")
	private Parking parking;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_type_id")
	private VehicleType vehicleType;
	
	@Column(name = "min_minutes")
	private Long min_minutes;
	
	@Column(name = "max_minutes")
	private Long max_minutes;
	
	@Column(name ="price")
	private BigDecimal price;
	
	@Column(name = "is_active")
	private Boolean is_active;
	
	@Column(name = "created_at")
	private LocalDate createdDate;
}
