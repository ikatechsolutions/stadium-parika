package com.stadium_parika.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stadium_parika.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
	
	@Query(value = "select v from VehicleType v where v.id = :id")
	VehicleType findVehicleTypeById(@Param("id") Long id);

	@Query(value = "select v from VehicleType v order by v.id desc ")
	Page<VehicleType> findAllVehiculeType(Pageable pageable);
	
	@Query("SELECT v FROM VehicleType v WHERE v.isActive = true")
	List<VehicleType> findAllActive();

	//JPQL query
	@Query(value = "select v from VehicleType v where v.name = :name")
	Optional<VehicleType> findVehicleTypeByName(@Param("name") String name);

	@Query(value = "select v from VehicleType v where UPPER(v.name) like CONCAT('%',UPPER(?1),'%' ) order by v.id desc ")
	Page<VehicleType> findByVehiculeTypeNameLike(String search, Pageable pageable);
}
