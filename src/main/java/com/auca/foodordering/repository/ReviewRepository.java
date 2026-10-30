package com.auca.foodordering.repository;

import com.auca.foodordering.entity.MealOrder;
import com.auca.foodordering.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    // E2 - JPQL: path navigation r.mealOrder.customer.phone + ORDER BY newest first
    @Query("SELECT r FROM Review r WHERE r.mealOrder.customer.phone = :phone ORDER BY r.reviewDate DESC")
    List<Review> findReviewsByCustomerPhone(@Param("phone") String phone);

    // E3 - DERIVED: does this meal order have any reviews?
    boolean existsByMealOrder(MealOrder mealOrder);
}