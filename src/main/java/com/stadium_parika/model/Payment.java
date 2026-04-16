package com.stadium_parika.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "parking_session_id", nullable = false)
	private ParkingSession parkingSession;
	
	@ManyToOne
	@JoinColumn(name = "subscription_id", nullable = false)
	private Subscription subscription;
	
	@NotNull(message = "Le montant est obligatoire")
	@DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit etre supérieur à 0")
	@Column(name = "amount", nullable = false)
	private BigDecimal amount;
	
	@NotNull(message = "La methode de paiement est obligatoire")
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", nullable = false)
	private PaymentMethodEnum paymentMethod;
	
	@NotBlank(message = "La reference est obligatoire")
	@Column(name = "reference", nullable = false)
	private String reference;
	
	@NotNull(message = "Le type est obligatoire")
	@Enumerated(EnumType.STRING)
	private TypeEnum type;
	
	@NotNull(message = "Le status est obligatoire")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@ManyToOne
	@JoinColumn(name = "received_by", nullable = false)
	private User receivedBy;
	
	@PastOrPresent(message = "La date de création est invalide")
	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;
	
	@PrePersist
	public void prePersist() {
	    this.createdAt = LocalDate.now();
	}
}
