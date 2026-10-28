package com.auca.foodordering.service;

import com.auca.foodordering.entity.DeliveryZone;
import com.auca.foodordering.repository.DeliveryZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryZoneService {

    private final DeliveryZoneRepository deliveryZoneRepository; 

    public DeliveryZoneService(DeliveryZoneRepository deliveryZoneRepository) {  this.deliveryZoneRepository = deliveryZoneRepository;    
    }    
    public String save(DeliveryZone deliveryZone) {  
        deliveryZoneRepository.save(deliveryZone);        
        return "Delivery zone saved successfully";   
    }

    public List<DeliveryZone> findAll() {        
            return deliveryZoneRepository.findAll();   
    }  
    public Optional<DeliveryZone> findById(UUID id) {        
            return deliveryZoneRepository.findById(id);    
    }

    public Optional<DeliveryZone> update(UUID id, DeliveryZone deliveryZone) {        
        Optional<DeliveryZone> existing = deliveryZoneRepository.findById(id);      
        if (existing.isEmpty()) return Optional.empty(); 

        existing.get().setName(deliveryZone.getName());       
        return Optional.of(deliveryZoneRepository.save(existing.get()));   
     }    
    public String delete(UUID id) {        
        Optional<DeliveryZone> existing = deliveryZoneRepository.findById(id);       
        if (existing.isEmpty()) return "The delivery zone with that id does not exist";   

        deliveryZoneRepository.deleteById(id);        
        return "Delivery zone deleted successfully";    
    }
}