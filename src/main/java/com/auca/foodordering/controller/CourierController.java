package com.auca.foodordering.controller;

import com.auca.foodordering.entity.Courier;
import com.auca.foodordering.service.CourierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCourier(@RequestBody Courier courier) {
        String message = courierService.saveCourier(courier);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Courier>> getAllCouriers() {
        return new ResponseEntity<>(courierService.getAllCouriers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourierById(@PathVariable UUID id) {
        Optional<Courier> courier = courierService.getCourierById(id);
        if (courier.isPresent()) {
            return new ResponseEntity<>(courier.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The courier with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCourier(@PathVariable UUID id, @RequestBody Courier courier) {
        String message = courierService.updateCourier(id, courier);
        if (message.equals("The courier with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourier(@PathVariable UUID id) {
        String message = courierService.deleteCourier(id);
        if (message.equals("The courier with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}