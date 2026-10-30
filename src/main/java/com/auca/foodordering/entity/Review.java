package com.auca.foodordering.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String dishName;

    private String comment;

    private int stars;

    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name = "meal_order_id")
    private MealOrder mealOrder;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }

    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }

    public MealOrder getMealOrder() { return mealOrder; }
    public void setMealOrder(MealOrder mealOrder) { this.mealOrder = mealOrder; }
}