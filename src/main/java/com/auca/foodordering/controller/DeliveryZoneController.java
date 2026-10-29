package com.auca.foodordering.controller;

import com.auca.foodordering.entity.DeliveryZone;
import com.auca.foodordering.service.DeliveryZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-zones")
public class DeliveryZoneController {    
    
    private final DeliveryZoneService deliveryZoneService;    
    
    public DeliveryZoneController(DeliveryZoneService deliveryZoneService) {        
        this.deliveryZoneService = deliveryZoneService;   
    
    }    
    @PostMapping("/save")    
    public ResponseEntity<String> save(@RequestBody DeliveryZone deliveryZone) {       
        return new ResponseEntity<>(deliveryZoneService.save(deliveryZone), HttpStatus.CREATED);    
    
    }    
    
    @GetMapping("/all")    
    public ResponseEntity<List<DeliveryZone>> all() {       
        return new ResponseEntity<>(deliveryZoneService.findAll(), HttpStatus.OK);    
    
    }    
    
    @GetMapping("/{id}")    
    public ResponseEntity<?> getById(@PathVariable UUID id) {        
        Optional<DeliveryZone> dz = deliveryZoneService.findById(id);        
        if (dz.isEmpty()) {            
            return new ResponseEntity<>("The delivery zone with that id does not exist", HttpStatus.NOT_FOUND);        
        
        }        
        
        return new ResponseEntity<>(dz.get(), HttpStatus.OK);    
    } 
    
    @PutMapping("/update/{id}")    
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody DeliveryZone deliveryZone) {        
        Optional<DeliveryZone> updated = deliveryZoneService.update(id, deliveryZone);        
        if (updated.isEmpty()) {            
            
            return new ResponseEntity<>("The delivery zone with that id does not exist", HttpStatus.NOT_FOUND);        
        
        }        
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);    }    
            @DeleteMapping("/delete/{id}")    
            public ResponseEntity<String> delete(@PathVariable UUID id) {   String message = deliveryZoneService.delete(id);        
                if (message.equals("The delivery zone with that id does not exist")) {            
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);        
                
            }        
                return new ResponseEntity<>(message, HttpStatus.OK);    
            }
        }