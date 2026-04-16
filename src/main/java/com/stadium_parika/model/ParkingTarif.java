package com.stadium_parika.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
	
	@NotNull(message = "Le minimum minutes est obligatoire")
	@Positive(message = "Le minimum minutes doit etre supérieur à 0")
	@Column(name = "min_minutes")
	private Integer minMinutes;
	
	@NotNull(message = "Le maximum minutes est obligatoire")
	@Positive(message = "Le maximum minutes doit etre supérieur à 0")
	@Column(name = "max_minutes")
	private Integer maxMinutes;
	
	@NotNull(message = "Le prix est obligatoire")
	@Positive(message = "Le prix doit etre supérieur à 0")
	@Column(name ="price")
	private BigDecimal price;
	
	@NotNull(message = "is_active est obligatoire")
	@Column(name = "is_active")
	private Boolean is_active;
	
	@PastOrPresent(message = "La date de création est obligatoire")
	@Column(name = "created_at")
	private LocalDate createdDate;
	
	@PrePersist
    public void prePersist() {
        this.createdDate = LocalDate.now();
    }
}
