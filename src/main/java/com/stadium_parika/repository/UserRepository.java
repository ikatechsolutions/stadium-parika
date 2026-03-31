package com.stadium_parika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stadium_parika.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "select u from User u where u.id = :id")
	User findCreatedById(@Param("id") Long id);
}
