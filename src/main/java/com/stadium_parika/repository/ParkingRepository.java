package com.stadium_parika.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stadium_parika.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
	
	@Query(value = "select p from Parking p where p.id = :id")
	Parking findParkingById(@Param("id") Long id);
	
	@Query(value = "select p from Parking p order by p.id desc")
	Page<Parking> findAllParking(Pageable pageable);
	
	@Query(value = "select p from Parking p where p.name = :name")
	Optional<Parking> findParkingByName(@Param("name") String name);
	
	@Query(value = "select p from Parking p where UPPER(p.name) like CONCAT('%',UPPER(?1),'%' ) order by p.id desc")
	Page<Parking> findByParkingNameLike(String search, Pageable pageable);
}
