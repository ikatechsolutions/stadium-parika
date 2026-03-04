package com.stadium_parika.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parkings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parking extends AbstractEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "capacity")
	private Long capacity;
	
	@Column(name = "has_slots", nullable = true)
	private Boolean has_slots;
	
	@Column(name = "is_active")
	private Boolean is_active;
	
	@Column(name = "created_at")
    private LocalDate creationDate;
	
	@Column(name = "updated_at")
	private LocalDate updatedDate;
}
