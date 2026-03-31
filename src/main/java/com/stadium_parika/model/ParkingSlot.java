package com.stadium_parika.model;

import java.time.LocalDate;

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
@Table(name = "parking_slots")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlot extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "parking_id")
	private Parking parking;
	
	@Column(name = "slot_code")
	private String slotCode;
	
	@Enumerated(EnumType.STRING)
	private ParkingSlotStatusEnum status;
	
	@Column(name = "created_at")
    private LocalDate creationDate;
}
