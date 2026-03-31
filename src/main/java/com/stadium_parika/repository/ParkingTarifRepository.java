package com.stadium_parika.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stadium_parika.model.ParkingTarif;

public interface ParkingTarifRepository extends JpaRepository<ParkingTarif, Long> {
	
	@Query(value = "select p from ParkingTarif p where p.id = :id AND p.parking.id =:parking_id")
    ParkingTarif findParkingTarifById(@Param("id") Long id,@Param("parking_id") Long parking_id);


    @Query(value = "select p from ParkingTarif p where p.vehicleType.id =:vehicule_type_id AND p.parking.id = :parking_id ")
    Optional<ParkingTarif> findParkingTarifByParkingAndVehiculeType(@Param("parking_id") Long parking_id,@Param("vehicule_type_id") Long vehicule_type_id);
    
    @Query(value = "select p from ParkingTarif p where p.parking.id=?1 order by p.id desc ")
    Page<ParkingTarif> findParkingTarifs(Long idParking, Pageable pageable);

    @Query("select p from ParkingTarif p where p.parking.id=?1 AND UPPER(p.vehicleType.name) like CONCAT('%',UPPER(?2),'%' ) order by p.id desc")
    Page<ParkingTarif> findByNameLike(Long idParking,String search, Pageable pageable);

    List<ParkingTarif> findAllByVehicleTypeId(Long vehicleType_id);
    
    List<ParkingTarif> findAllByParkingId(Long parking_id);
    
    @Query(value = "select p from ParkingTarif p where p.parking.id=?1 order by p.id desc ")
    List<ParkingTarif> findByParkingTarifs(Long idParking);
    
    // Pour chevauchement
    @Query("SELECT t FROM ParkingTarif t " +
    	       "WHERE t.parking.id = :parkingId " +
    	       "AND t.vehicleType.id = :vehicleTypeId " +
    	       "AND (:min <= t.maxMinutes AND :max >= t.minMinutes)")
	List<ParkingTarif> findOverlappingTarifs(
	        @Param("parkingId") Long parkingId,
	        @Param("vehicleTypeId") Long vehicleTypeId,
	        @Param("min") Integer min,
	        @Param("max") Integer max
	);
    
    // Pour desactivation des anciens tarifs
    @Modifying
    @Query("UPDATE ParkingTarif t SET t.is_active = false " +
           "WHERE t.parking.id = :parkingId " +
           "AND t.vehicleType.id = :vehicleTypeId " +
           "AND t.minMinutes = :min " +
           "AND t.maxMinutes = :max " +
           "AND (:id IS NULL OR t.id <> :id)")
    void deactivateExistingTarifs(
            @Param("parkingId") Long parkingId,
            @Param("vehicleTypeId") Long vehicleTypeId,
            @Param("min") Integer min,
            @Param("max") Integer max,
            @Param("id") Long id
    );
    
    @Query("SELECT t FROM ParkingTarif t " +
    	       "WHERE t.parking.id = :parkingId " +
    	       "AND t.vehicleType.id = :vehicleTypeId " +
    	       "AND t.is_active = true " +
    	       "AND t.minMinutes <= :duration " +
    	       "ORDER BY t.maxMinutes DESC")
	List<ParkingTarif> findTarifsForDuration(
	        @Param("parkingId") Long parkingId,
	        @Param("vehicleTypeId") Long vehicleTypeId,
	        @Param("duration") Integer duration
	);
}
