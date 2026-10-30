package com.auca.foodordering.repository;

import com.auca.foodordering.entity.Courier;
import com.auca.foodordering.entity.MealOrder;
import com.auca.foodordering.entity.MealOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // F1 - JPQL: GROUP BY + COUNT, one row per status
    @Query("SELECT m.status, COUNT(m) FROM MealOrder m GROUP BY m.status")
    List<Object[]> countMealOrdersByStatus();

    // F2 - JPQL: one UPDATE query, cancels everything except DELIVERED
    @Modifying
    @Query("UPDATE MealOrder m SET m.status = com.auca.foodordering.entity.MealOrderStatus.CANCELLED " +
           "WHERE m.courier.id = :courierId AND m.orderDate = :date " +
           "AND m.status <> com.auca.foodordering.entity.MealOrderStatus.DELIVERED")
    int cancelDay(@Param("courierId") UUID courierId, @Param("date") LocalDate date);

    // BONUS dashboard - DERIVED countBy
    long countByOrderDate(LocalDate orderDate);

    long countByStatus(MealOrderStatus status);
}