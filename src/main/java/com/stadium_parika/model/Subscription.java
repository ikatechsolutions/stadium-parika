package com.stadium_parika.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "subscription_plan_id", nullable = false)
	private SubscriptionPlan subscriptionPlan;
	
	@NotBlank(message = "Le plate number est obligatoire")
	@Column(name = "plate_number", nullable = false)
	private String plateNumber;
	
	@NotNull(message = "La date de début est obligatoire")
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	
	@NotNull(message = "La date de fin est obligatoire")
	@Future(message = "La date de fin doit etre dans le futur")
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;
	
	@NotNull(message = "Le status est obligatoire")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private SubscriptionStatus status;
	
	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;
	
	@PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }
	
	@AssertTrue(message = "La date de fin doit être après la date de début")
	public boolean isDateValid() {
	    if (startDate == null || endDate == null) {
	        return true; // handled by @NotNull
	    }
	    return endDate.isAfter(startDate);
	}
}
