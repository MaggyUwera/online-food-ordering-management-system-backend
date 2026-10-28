package com.auca.foodordering.repository;

import com.auca.foodordering.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
    
}