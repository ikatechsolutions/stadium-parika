package com.stadium_parika.dto;

import java.time.LocalDate;

import com.stadium_parika.model.User;
import com.stadium_parika.model.UserRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Long id;
	private String name;
	private String phone;
	private String email;
	private UserRoleEnum role;
	private ParkingDto parking;
	private Boolean isActive;
	private LocalDate createdAt;
	
	public static UserDto fromEntity(User user) {
		if (user == null) {
			return null;
		}
		
		return UserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
				.role(user.getRole())
				.parking(ParkingDto.fromEntity(user.getParking()))
				.isActive(user.getIsActive())
				.createdAt(user.getCreatedAt())
				.build();
	}
	
	public static User toEntity(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		
		User users = new User();
		users.setId(userDto.getId());
		users.setName(userDto.getName());
		users.setPhone(userDto.getPhone());
		users.setEmail(userDto.getEmail());
		users.setRole(userDto.getRole());
		users.setParking(ParkingDto.toEntity(userDto.getParking()));
		users.setIsActive(userDto.getIsActive());
		users.setCreatedAt(userDto.getCreatedAt());
		
		return users;
	}
}
