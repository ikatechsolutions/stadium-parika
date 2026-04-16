package com.stadium_parika.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parkings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parking extends AbstractEntity {
	
	@NotBlank(message = "Le nom est obligatoire")
	@Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@NotBlank(message = "La localisation est obligatoire")
	@Size(min = 2, max = 255, message = "La localisation dépasse 255 caractères")
	@Column(name = "location", nullable = false)
	private String location;
	
	@NotNull(message = "La capacité est obligatoire")
	@Positive(message = "La capacité doit etre supérieur à 0")
	@Column(name = "capacity", nullable = false)
	private Integer capacity;
	
	@NotNull(message = "has_slots est obligatoire")
	@Column(name = "has_slots", nullable = false)
	private Boolean has_slots;
	
	@NotNull(message = "Le statut actif est obligatoire")
	@Column(name = "is_active")
	private Boolean is_active;
	
	@PastOrPresent(message = "Date de création invalide")
	@Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate creationDate;
	
	@PastOrPresent(message = "Date de mise à jour invalide")
	@Column(name = "updated_at")
	private LocalDate updatedDate;
	
	// Auto gestion des dates
    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDate.now();
        this.updatedDate = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDate.now();
    }
}
