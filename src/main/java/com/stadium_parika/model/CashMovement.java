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

@Entity
@Table(name = "cash_movements")
public class CashMovement extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "opening_balance", nullable = false)
	private BigDecimal openingBalance;
	
	@Column(name = "closing_balance")
	private BigDecimal closingBalance;
	
	@Column(name = "total_cash_received")
	private BigDecimal totalCashReceived;
	
	@Column(name = "opened_at", nullable = false)
	private LocalDate openedAt;
	
	@Column(name = "closed_at")
	private LocalDate closedAt;
	
	@Enumerated(EnumType.STRING)
	private CashMovementStatus status;
}
