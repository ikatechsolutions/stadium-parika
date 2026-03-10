package com.stadium_parika.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stadium_parika.model.ParkingSlot;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
	
	@Query(value = "select pl from ParkingSlot pl where pl.id = :id")
	ParkingSlot findParkingSlotById(@Param("id") Long id);
	
	@Query(value = "select pl from ParkingSlot pl order by pl.id desc")
	Page<ParkingSlot> findAllParkingSlot(Pageable pageable);
	
//	@Query(value = "select pl from ParkingSlot pl where pl.name = :name")
//	Optional<ParkingSlot> findParkingSlotByName(@Param("name") String name);
	
//	@Query(value = "select pl from ParkingSlot pl where UPPER(pl.name) like CONCAT('%',UPPER(?1),'%' ) order by pl.id desc")
//	Page<ParkingSlot> findByParkingSlotNameLike(String search, Pageable pageable);
}
