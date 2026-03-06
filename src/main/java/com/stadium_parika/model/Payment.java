package com.stadium_parika.model;

import java.math.BigDecimal;
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
	
	@Column(name = "amount", nullable = false)
	private BigDecimal amount;
	
	@Column(name = "payment_method", nullable = false)
	private PaymentMethodEnum paymentMethod;
	
	@Column(name = "reference", nullable = true)
	private String reference;
	
	@Enumerated(EnumType.STRING)
	private TypeEnum type;
	
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@ManyToOne
	@JoinColumn(name = "received_by", nullable = false)
	private User receivedBy;
	
	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;
}
