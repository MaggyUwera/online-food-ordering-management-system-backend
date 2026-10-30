package com.auca.foodordering.controller;

import com.auca.foodordering.entity.MealOrder;
import com.auca.foodordering.entity.MealOrderStatus;
import com.auca.foodordering.service.MealOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/meal-orders")
public class MealOrderController {

    private final MealOrderService mealOrderService;

    public MealOrderController(MealOrderService mealOrderService) {
        this.mealOrderService = mealOrderService;
    }

    // A2 + D1 - POST /api/meal-orders/save
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody MealOrder mealOrder) {
        String message = mealOrderService.save(mealOrder);
        if (message.equals("Meal order date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else if (message.equals("Courier already has a delivery on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // A2 - GET /api/meal-orders/all
    @GetMapping("/all")
    public ResponseEntity<List<MealOrder>> all() {
        return new ResponseEntity<>(mealOrderService.findAll(), HttpStatus.OK);
    }

    // B2 - GET /api/meal-orders/by-status?status=PLACED
    @GetMapping("/by-status")
    public ResponseEntity<List<MealOrder>> getMealOrdersByStatus(@RequestParam MealOrderStatus status) {
        return new ResponseEntity<>(mealOrderService.getMealOrdersByStatus(status), HttpStatus.OK);
    }

    // B3 - GET /api/meal-orders/between?start=2026-10-01&end=2026-10-31
    @GetMapping("/between")
    public ResponseEntity<List<MealOrder>> getMealOrdersBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(mealOrderService.getMealOrdersBetween(startDate, endDate), HttpStatus.OK);
    }

    // F1 - GET /api/meal-orders/stats/by-status
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getMealOrderStatsByStatus() {
        return new ResponseEntity<>(mealOrderService.getMealOrderStatsByStatus(), HttpStatus.OK);
    }

    // BONUS - GET /api/meal-orders/page?page=0&size=5&sort=orderDate,desc
    @GetMapping("/page")
    public ResponseEntity<Page<MealOrder>> getMealOrdersPage(@RequestParam int page,
                                                             @RequestParam int size,
                                                             @RequestParam String sort) {
        String[] sortParts = sort.split(",");                 // "orderDate,desc" -> ["orderDate", "desc"]
        Sort.Direction direction = Sort.Direction.fromString(sortParts[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParts[0]));
        return new ResponseEntity<>(mealOrderService.getMealOrdersPage(pageable), HttpStatus.OK);
    }

    // A2 - GET /api/meal-orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<MealOrder> m = mealOrderService.findById(id);
        if (m.isEmpty()) {
            return new ResponseEntity<>("The meal order with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(m.get(), HttpStatus.OK);
    }

    // A2 - PUT /api/meal-orders/update/{id} (does not change the status)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody MealOrder mealOrder) {
        String message = mealOrderService.update(id, mealOrder);
        if (message.equals("The meal order with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // D2 - PATCH /api/meal-orders/{id}/status?status=ON_THE_WAY
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam MealOrderStatus status) {
        String message = mealOrderService.updateStatus(id, status);
        if (message.equals("The meal order with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.startsWith("Cannot change status from")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // F2 - PATCH /api/meal-orders/cancel-day?courierId=...&date=2026-10-20
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID courierId, @RequestParam String date) {
        LocalDate day = LocalDate.parse(date);
        return new ResponseEntity<>(mealOrderService.cancelDay(courierId, day), HttpStatus.OK);
    }

    // A2 + E3 - DELETE /api/meal-orders/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = mealOrderService.delete(id);
        if (message.equals("The meal order with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("That meal order has reviews, delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}