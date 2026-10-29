package com.auca.foodordering.controller;

import com.auca.foodordering.entity.Motorbike;
import com.auca.foodordering.service.MotorbikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/motorbikes")
public class MotorbikeController {

    private final MotorbikeService motorbikeService;

    public MotorbikeController(MotorbikeService motorbikeService) {
        this.motorbikeService = motorbikeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Motorbike motorbike) {
        return new ResponseEntity<>(motorbikeService.save(motorbike), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Motorbike>> all() {
        return new ResponseEntity<>(motorbikeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Motorbike> m = motorbikeService.findById(id);
        if (m.isEmpty()) {
            return new ResponseEntity<>("The motorbike with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(m.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Motorbike motorbike) {
        Optional<Motorbike> updated = motorbikeService.update(id, motorbike);
        if (updated.isEmpty()) {
            return new ResponseEntity<>("The motorbike with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated.get(), HttpStatus.OK);
    }
    // C2 - GET /api/motorbikes/free
    @GetMapping("/free")
    public ResponseEntity<List<Motorbike>> getFreeMotorbikes() {
        return new ResponseEntity<>(motorbikeService.getFreeMotorbikes(), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = motorbikeService.delete(id);
        if (message.equals("The motorbike with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}