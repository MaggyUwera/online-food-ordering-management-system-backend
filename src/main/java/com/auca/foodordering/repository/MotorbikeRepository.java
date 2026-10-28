package com.auca.foodordering.repository;

import com.auca.foodordering.entity.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MotorbikeRepository extends JpaRepository<Motorbike, UUID> {
    
}