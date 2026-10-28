package com.auca.foodordering.service;

import com.auca.foodordering.entity.Courier;
import com.auca.foodordering.repository.CourierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourierService {    
    private final CourierRepository courierRepository;    
    
    public CourierService(CourierRepository courierRepository) { 
         this.courierRepository = courierRepository; 
    }   
    public String save(Courier courier) { courierRepository.save(courier);       
    return "Courier saved successfully";    
}    
public List<Courier> findAll() {    
    return courierRepository.findAll();   

}   

public Optional<Courier> findById(UUID id) {       
     return courierRepository.findById(id);   
 }    
public Optional<Courier> update(UUID id, Courier courier) {   
    Optional<Courier> existing = courierRepository.findById(id);     
    if (existing.isEmpty()) return Optional.empty();        
            
    existing.get().setFirstName(courier.getFirstName());        
    existing.get().setLastName(courier.getLastName());       
    existing.get().setPhone(courier.getPhone());       
    existing.get().setMotorbike(courier.getMotorbike());       
    existing.get().setDeliveryZones(courier.getDeliveryZones());       
    return Optional.of(courierRepository.save(existing.get()));   
}   

public String delete(UUID id) {       
    Optional<Courier> existing = courierRepository.findById(id);       
    if (existing.isEmpty()) return "The courier with that id does not exist";      
                    
    courierRepository.deleteById(id);        
    return "Courier deleted successfully";    
                
    }
}