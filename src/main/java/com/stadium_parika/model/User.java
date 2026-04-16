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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {
	
	@NotBlank(message = "Le nom est obligatoire")
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotBlank(message = "Le numero de telephone est obligatoire")
	@Column(name = "phone", unique = true, nullable = false)
	private String phone;
	
	@NotBlank(message = "l'email est obligatoire")
	@Email(message = "Format d'email invalide")
	@Column(name ="email", unique = true, nullable = false)
	private String email;
	
	@NotBlank(message = "Le mot de passe est obligatoire")
	@Column(name = "password", nullable = false)
	private String password;
	
	@NotNull(message= "Le role est obligatoire")
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable= false)
	private UserRoleEnum role;
	
	@ManyToOne
	@JoinColumn(name = "parking_id", nullable = true)
	private Parking parking;
	
	@Column(name = "is_active")
	private Boolean isActive = true;
	
	@PastOrPresent(message = "Date invalide")
	@Column(name = "created_at")
	private LocalDate createdAt;
	
	@PrePersist
	public void prePersist() {
	    this.createdAt = LocalDate.now();
	}
}
