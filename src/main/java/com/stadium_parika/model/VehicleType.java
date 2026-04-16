package com.stadium_parika.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleType extends AbstractEntity {
	
	@NotBlank(message = "Le nom est obligatoire")
	@Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
	@Column(name = "name", nullable = false)
	private String name;
	
	@Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
	@Column(name = "description", nullable = true)
	private String description;
	
	@NotBlank(message = "La photo est obligatoire")
	@Column(name = "photo", nullable = false)
	private String photo;
	
	@NotNull(message = "Le status actif est obligatoire")
	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;
	
	@PastOrPresent(message = "La date de création doit etre dans le passé ou présent")
	@Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime creationDate;
	
	@PrePersist
	public void prePersist() {
	    this.creationDate = LocalDateTime.now();
	}
}
