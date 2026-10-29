package com.auca.foodordering.service;

import com.auca.foodordering.entity.Motorbike;
import com.auca.foodordering.repository.MotorbikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MotorbikeService {    
    
    private final MotorbikeRepository motorbikeRepository;    
    
    public MotorbikeService(MotorbikeRepository motorbikeRepository) { 
         this.motorbikeRepository = motorbikeRepository; 
    }   
    public String save(Motorbike motorbike) { motorbikeRepository.save(motorbike);       
    return "Motorbike saved successfully";    
}    
public List<Motorbike> findAll() {    
    return motorbikeRepository.findAll();   

}   

public Optional<Motorbike> findById(UUID id) {       
     return motorbikeRepository.findById(id);   
 }    
public Optional<Motorbike> update(UUID id, Motorbike motorbike) {   
    Optional<Motorbike> existing = motorbikeRepository.findById(id);     
    if (existing.isEmpty()) return Optional.empty();        
            
    existing.get().setBikeNumber(motorbike.getBikeNumber());        
    existing.get().setPlateNumber(motorbike.getPlateNumber());       
    return Optional.of(motorbikeRepository.save(existing.get()));   
}   

    // C2 - motorbikes that no courier occupies
    public List<Motorbike> getFreeMotorbikes() {
        return motorbikeRepository.findFreeMotorbikes();
    }
public String delete(UUID id) {       
    Optional<Motorbike> existing = motorbikeRepository.findById(id);       
    if (existing.isEmpty()) return "The motorbike with that id does not exist";      
                    
    motorbikeRepository.deleteById(id);        
    return "Motorbike deleted successfully";    
                
    }
}