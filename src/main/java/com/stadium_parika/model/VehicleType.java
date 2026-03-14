package com.stadium_parika.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleType extends AbstractEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "photo", nullable = false)
	private String photo;
	
	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;
	
	@Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime creationDate;
}
