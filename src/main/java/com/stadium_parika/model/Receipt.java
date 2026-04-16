package com.stadium_parika.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
	
	@NotBlank(message = "Le receipt number est obligatoire")
	@Column(name = "receipt_number", nullable = false)
	private String receiptNumber;
	
	@PastOrPresent(message = "issued_at est invalide")
	@Column(name = "issued_at", nullable = false)
	private LocalDateTime issuedAt;
	
	@ManyToOne
	@JoinColumn(name = "issued_by", nullable = false)
	private User issuedBy;
	
	@NotNull(message = "Le receipt status est obligatoire")
	@Enumerated(EnumType.STRING)
	private ReceiptStatusEnum status;
}
