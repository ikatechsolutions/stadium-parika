package com.stadium_parika.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stadium_parika.model.ParkingSession;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
	
	@Query(value = "select ps from ParkingSession ps where ps.id = :id")
	ParkingSession findParkingSessionById(@Param("id") Long id);
	
	@Query(value = "select ps from ParkingSession ps order by ps.id desc")
	Page<ParkingSession> findAllParkingSession(Pageable pageable);
	
	@Query(value = "select ps from ParkingSession ps where ps.plateNumber = :plate_number")
	Optional<ParkingSession> findParkingSessionByPlateNumber(@Param("plate_number") String plateNumber);
	
	@Query(value = "select ps from ParkingSession ps where UPPER(ps.plateNumber) like CONCAT('%',UPPER(?1),'%' ) order by ps.id desc")
	Page<ParkingSession> findByParkingSessionPlateNumberLike(String search, Pageable pageable);
	
	boolean existsByParkingId(Long parkingId);
}
