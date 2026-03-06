package com.stadium_parika.model;

import java.time.LocalDateTime;

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
@Table(name = "receipts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receipt extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;
	
	@Column(name = "receipt_number", nullable = false)
	private String receiptNumber;
	
	@Column(name = "issued_at", nullable = false)
	private LocalDateTime issuedAt;
	
	@ManyToOne
	@JoinColumn(name = "issued_by", nullable = false)
	private User issuedBy;
	
	@Enumerated(EnumType.STRING)
	private ReceiptStatusEnum status;
}
