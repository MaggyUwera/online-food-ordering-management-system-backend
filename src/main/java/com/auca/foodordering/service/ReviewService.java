package com.auca.foodordering.service;

import com.auca.foodordering.entity.MealOrder;
import com.auca.foodordering.entity.MealOrderStatus;
import com.auca.foodordering.entity.Review;
import com.auca.foodordering.repository.MealOrderRepository;
import com.auca.foodordering.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MealOrderRepository mealOrderRepository;

    public ReviewService(ReviewRepository reviewRepository, MealOrderRepository mealOrderRepository) {
        this.reviewRepository = reviewRepository;
        this.mealOrderRepository = mealOrderRepository;
    }

    // E1 - write a review for a DELIVERED meal order
    public String saveReview(Review review, UUID mealOrderId) {
        Optional<MealOrder> mealOrderOptional = mealOrderRepository.findById(mealOrderId);
        if (mealOrderOptional.isEmpty()) {
            return "The meal order with that id does not exist";
        }

        MealOrder mealOrder = mealOrderOptional.get();
        if (mealOrder.getStatus() != MealOrderStatus.DELIVERED) {
            return "Reviews are only for DELIVERED meal orders";
        }

        review.setReviewDate(LocalDate.now()); // the service sets the date to today
        review.setMealOrder(mealOrder);        // and links the meal order
        reviewRepository.save(review);
        return "Review saved successfully";
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // E2 - review history of a customer, newest first
    public List<Review> getReviewsOfCustomer(String phone) {
        return reviewRepository.findReviewsByCustomerPhone(phone);
    }

    // E3 - delete a review
    public String deleteReview(UUID id) {
        if (!reviewRepository.existsById(id)) {
            return "The review with that id does not exist";
        }
        reviewRepository.deleteById(id);
        return "Review deleted successfully";
    }
}