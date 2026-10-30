package com.auca.foodordering.service;

import com.auca.foodordering.entity.MealOrderStatus;
import com.auca.foodordering.repository.CourierRepository;
import com.auca.foodordering.repository.CustomerRepository;
import com.auca.foodordering.repository.MealOrderRepository;
import com.auca.foodordering.repository.MotorbikeRepository;
import com.auca.foodordering.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final CourierRepository courierRepository;
    private final MotorbikeRepository motorbikeRepository;
    private final MealOrderRepository mealOrderRepository;
    private final ReviewRepository reviewRepository;

    public DashboardService(CustomerRepository customerRepository,
                            CourierRepository courierRepository,
                            MotorbikeRepository motorbikeRepository,
                            MealOrderRepository mealOrderRepository,
                            ReviewRepository reviewRepository) {
        this.customerRepository = customerRepository;
        this.courierRepository = courierRepository;
        this.motorbikeRepository = motorbikeRepository;
        this.mealOrderRepository = mealOrderRepository;
        this.reviewRepository = reviewRepository;
    }

    // BONUS - one JSON object with the key numbers of the system
    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>(); // keeps the insertion order

        summary.put("customers", customerRepository.count());
        summary.put("couriers", courierRepository.count());
        summary.put("freeMotorbikes", (long) motorbikeRepository.findFreeMotorbikes().size()); // reuses C2
        summary.put("ordersToday", mealOrderRepository.countByOrderDate(LocalDate.now()));
        summary.put("placed", mealOrderRepository.countByStatus(MealOrderStatus.PLACED));
        summary.put("reviews", reviewRepository.count());

        return summary;
    }
}