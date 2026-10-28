package com.auca.foodordering.service;

import com.auca.foodordering.entity.MealOrder;
import com.auca.foodordering.repository.MealOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MealOrderService {    
    private final MealOrderRepository mealOrderRepository;   
    
    public MealOrderService(MealOrderRepository mealOrderRepository) { this.mealOrderRepository = mealOrderRepository; 

    }   
     
    public String save(MealOrder mealOrder) {       
        mealOrderRepository.save(mealOrder);       
        return "Meal order saved successfully";   
    }    
    
    public List<MealOrder> findAll() {      
        return mealOrderRepository.findAll();   
    }    
    
    public Optional<MealOrder> findById(UUID id) {        
        return mealOrderRepository.findById(id);   
    }    
    
    public Optional<MealOrder> update(UUID id, MealOrder mealOrder) {        
        Optional<MealOrder> existing = mealOrderRepository.findById(id);        
        if (existing.isEmpty()) return Optional.empty();        
        
        existing.get().setOrderDate(mealOrder.getOrderDate());        
        existing.get().setStatus(mealOrder.getStatus());        
        existing.get().setItems(mealOrder.getItems());        
        existing.get().setTotalAmount(mealOrder.getTotalAmount());        
        existing.get().setCustomer(mealOrder.getCustomer());        
        existing.get().setCourier(mealOrder.getCourier());        
        
        return Optional.of(mealOrderRepository.save(existing.get()));    
    
    }    
    public String delete(UUID id) { 
        Optional<MealOrder> existing = mealOrderRepository.findById(id);        
        if (existing.isEmpty()) return "The meal order with that id does not exist";       
        
        mealOrderRepository.deleteById(id);        
        return "Meal order deleted successfully";    
    }
}