package com.auca.foodordering.controller;

import com.auca.foodordering.entity.Review;
import com.auca.foodordering.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // E1 - POST /api/reviews/save?mealOrderId=...
    @PostMapping("/save")
    public ResponseEntity<String> saveReview(@RequestParam UUID mealOrderId, @RequestBody Review review) {
        String message = reviewService.saveReview(review, mealOrderId);
        if (message.equals("The meal order with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("Reviews are only for DELIVERED meal orders")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // extra: list all reviews (handy for copying review ids)
    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    // E2 - GET /api/reviews/customer/{phone}
    @GetMapping("/customer/{phone}")
    public ResponseEntity<List<Review>> getReviewsOfCustomer(@PathVariable String phone) {
        return new ResponseEntity<>(reviewService.getReviewsOfCustomer(phone), HttpStatus.OK);
    }

    // E3 - DELETE /api/reviews/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable UUID id) {
        String message = reviewService.deleteReview(id);
        if (message.equals("The review with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}