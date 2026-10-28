package com.auca.foodordering.repository;

import com.auca.foodordering.entity.MealOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MealOrderRepository extends JpaRepository<MealOrder, UUID> {
    
}