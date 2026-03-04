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
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "phone", unique = true, nullable = false)
	private String phone;
	
	@Column(name ="email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable= false)
	private UserRoleEnum role;
	
	@ManyToOne
	@JoinColumn(name = "parking_id", nullable = true)
	private Parking parking;
	
	@Column(name = "is_active")
	private Boolean isActive = true;
	
	@Column(name = "created_at")
	private LocalDate createdAt;
}
