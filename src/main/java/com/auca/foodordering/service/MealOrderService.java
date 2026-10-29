package com.auca.foodordering.service;

import com.auca.foodordering.entity.MealOrder;
import com.auca.foodordering.entity.MealOrderStatus;
import com.auca.foodordering.repository.MealOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MealOrderService {

    private final MealOrderRepository mealOrderRepository;

    public MealOrderService(MealOrderRepository mealOrderRepository) {
        this.mealOrderRepository = mealOrderRepository;
    }

    // A2 + D1 - safe saving
    public String save(MealOrder mealOrder) {
        if (mealOrder.getOrderDate().isBefore(LocalDate.now())) {
            return "Meal order date cannot be in the past";
        }
        if (mealOrderRepository.existsByCourierAndOrderDateAndStatusNot(
                mealOrder.getCourier(), mealOrder.getOrderDate(), MealOrderStatus.CANCELLED)) {
            return "Courier already has a delivery on that date";
        }
        mealOrder.setStatus(MealOrderStatus.PLACED); // a new order always starts as PLACED
        mealOrderRepository.save(mealOrder);
        return "Meal order saved successfully";
    }

    public List<MealOrder> findAll() {
        return mealOrderRepository.findAll();
    }

    public Optional<MealOrder> findById(UUID id) {
        return mealOrderRepository.findById(id);
    }

    // A2 - general update: never changes the status
    public String update(UUID id, MealOrder mealOrder) {
        Optional<MealOrder> existing = mealOrderRepository.findById(id);
        if (existing.isEmpty()) return "The meal order with that id does not exist";

        existing.get().setOrderDate(mealOrder.getOrderDate());
        // status is NOT copied: only PATCH /api/meal-orders/{id}/status may change it (D2)
        existing.get().setItems(mealOrder.getItems());
        existing.get().setTotalAmount(mealOrder.getTotalAmount());
        existing.get().setCustomer(mealOrder.getCustomer());
        existing.get().setCourier(mealOrder.getCourier());

        mealOrderRepository.save(existing.get());
        return "Meal order updated successfully";
    }

    public String delete(UUID id) {
        Optional<MealOrder> existing = mealOrderRepository.findById(id);
        if (existing.isEmpty()) return "The meal order with that id does not exist";

        mealOrderRepository.deleteById(id);
        return "Meal order deleted successfully";
    }

    // B2 - meal orders by status, earliest orderDate first
    public List<MealOrder> getMealOrdersByStatus(MealOrderStatus status) {
        return mealOrderRepository.findByStatusOrderByOrderDateAsc(status);
    }

    // B3 - meal orders between two dates, both inclusive
    public List<MealOrder> getMealOrdersBetween(LocalDate start, LocalDate end) {
        return mealOrderRepository.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
    }

    // D2 - status flow: PLACED -> ON_THE_WAY or CANCELLED; ON_THE_WAY -> DELIVERED or CANCELLED
    public String updateStatus(UUID id, MealOrderStatus newStatus) {
        Optional<MealOrder> existing = mealOrderRepository.findById(id);
        if (existing.isEmpty()) return "The meal order with that id does not exist";

        MealOrder mealOrder = existing.get();
        MealOrderStatus currentStatus = mealOrder.getStatus();

        boolean allowed =
                (currentStatus == MealOrderStatus.PLACED
                        && (newStatus == MealOrderStatus.ON_THE_WAY || newStatus == MealOrderStatus.CANCELLED))
             || (currentStatus == MealOrderStatus.ON_THE_WAY
                        && (newStatus == MealOrderStatus.DELIVERED || newStatus == MealOrderStatus.CANCELLED));

        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        mealOrder.setStatus(newStatus);
        mealOrderRepository.save(mealOrder);
        return "Meal order status updated successfully";
    }
}