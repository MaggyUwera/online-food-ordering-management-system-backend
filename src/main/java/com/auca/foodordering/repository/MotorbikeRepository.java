package com.auca.foodordering.repository;

import com.auca.foodordering.entity.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MotorbikeRepository extends JpaRepository<Motorbike, UUID> {

    // C2 - JPQL: NOT EXISTS subquery, sorted by bikeNumber
    @Query("SELECT m FROM Motorbike m WHERE NOT EXISTS (SELECT c FROM Courier c WHERE c.motorbike = m) ORDER BY m.bikeNumber")
    List<Motorbike> findFreeMotorbikes();
}