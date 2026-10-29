package com.auca.foodordering.controller;

import com.auca.foodordering.entity.MealOrder;
import com.auca.foodordering.service.MealOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody MealOrder mealOrder) {
        return new ResponseEntity<>(mealOrderService.save(mealOrder), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MealOrder>> all() {
        return new ResponseEntity<>(mealOrderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<MealOrder> m = mealOrderService.findById(id);
        if (m.isEmpty()) {
            return new ResponseEntity<>("The meal order with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(m.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody MealOrder mealOrder) {
        Optional<MealOrder> updated = mealOrderService.update(id, mealOrder);
        if (updated.isEmpty()) {
            return new ResponseEntity<>("The meal order with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = mealOrderService.delete(id);
        if (message.equals("The meal order with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}