package com.auca.foodordering.repository;

import com.auca.foodordering.entity.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, UUID> {
    
}