package com.auca.foodordering.repository;

import com.auca.foodordering.entity.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, UUID> {

    // C3 - JPQL: IS EMPTY on the couriers collection (no counting)
    @Query("SELECT dz FROM DeliveryZone dz WHERE dz.couriers IS EMPTY")
    List<DeliveryZone> findUnusedDeliveryZones();
}