package com.auca.foodordering.repository;

import com.auca.foodordering.entity.Courier;
import com.auca.foodordering.entity.MealOrder;
import com.auca.foodordering.entity.MealOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MealOrderRepository extends JpaRepository<MealOrder, UUID> {

    // B2 - DERIVED: enum parameter + OrderBy
    List<MealOrder> findByStatusOrderByOrderDateAsc(MealOrderStatus status);

    // B3 - DERIVED: Between (both dates inclusive)
    List<MealOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);

    // D1 - DERIVED: existsBy + And + Not
    boolean existsByCourierAndOrderDateAndStatusNot(Courier courier, LocalDate orderDate, MealOrderStatus status);
}