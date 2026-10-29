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
     
    public CourierService(CourierRepository courierRepository) { this.courierRepository = courierRepository;    }    
     
    public String saveCourier(Courier courier) { courierRepository.save(courier);        
        return "Courier saved successfully";   
     }    
    public List<Courier> getAllCouriers() {        
        return courierRepository.findAll();    
    }    
    public Optional<Courier> getCourierById(UUID id) {        
        return courierRepository.findById(id);   
     }   
      public String updateCourier(UUID id, Courier updatedCourier) {      
          Optional<Courier> existing = courierRepository.findById(id);       
          if (existing.isEmpty()) {           
            return "The courier with that id does not exist";        
        }       
             Courier courier = existing.get();        
             courier.setFirstName(updatedCourier.getFirstName());        
             courier.setLastName(updatedCourier.getLastName());        
             courier.setPhone(updatedCourier.getPhone());        
             courier.setMotorbike(updatedCourier.getMotorbike());        
             courier.setDeliveryZones(updatedCourier.getDeliveryZones());        
             courierRepository.save(courier);        
             return "Courier updated successfully";   
             } 
                 // C1 - couriers who hold a delivery zone (name matched ignoring case)
    public List<Courier> getCouriersByDeliveryZone(String name) {
        return courierRepository.findCouriersByDeliveryZoneName(name);
    }   
             public String deleteCourier(UUID id) {      
                  if (!courierRepository.existsById(id)) {           
                     return "The courier with that id does not exist";       
                     }       
                      courierRepository.deleteById(id);       
                       return "Courier deleted successfully";    
                    }
                    
                }