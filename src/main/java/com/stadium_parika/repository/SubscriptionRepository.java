package com.stadium_parika.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stadium_parika.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	@Query("SELECT s FROM Subscription s " +
		       "WHERE s.plateNumber = :plate " +
		       "AND s.status = 'ACTIVE' " +
		       "AND :today BETWEEN s.startDate AND s.endDate " +
		       "AND s.subscriptionPlan.isActive = true " +
		       "AND s.subscriptionPlan.parking.id = :parkingId " +
		       "AND s.subscriptionPlan.vehicleType.id = :vehicleTypeId")
	Optional<Subscription> findValidSubscription(
	        @Param("plate") String plate,
	        @Param("parkingId") Long parkingId,
	        @Param("vehicleTypeId") Long vehicleTypeId,
	        @Param("today") LocalDate today
	);
}	
