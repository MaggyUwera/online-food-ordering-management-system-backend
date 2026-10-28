package com.auca.foodordering.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import java.util.UUID;

@Entity
@Table(name = "meal_order")
public class MealOrder {   

    @Id   
    @GeneratedValue(strategy = GenerationType.UUID)    
    private UUID id; 

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)   
    private MealOrderStatus status;    
       
    private String items; 

    private double totalAmount; 

    @ManyToOne   
    @JoinColumn(name = "customer_id")    
    private Customer customer;

    @ManyToOne   
    @JoinColumn(name = "courier_id")   
    private Courier courier;

    public UUID getId() { return id; }   
    public void setId(UUID id) { this.id = id; }

    public LocalDate getOrderDate() { return orderDate; }    
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; } 

    public MealOrderStatus getStatus() { return status; }  
    public void setStatus(MealOrderStatus status) { this.status = status; }

    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }

    public double getTotalAmount() { return totalAmount; }   
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
                    
    public Customer getCustomer() { return customer; }   
    public void setCustomer(Customer customer) { this.customer = customer; }  

    public Courier getCourier() { return courier; }   
    public void setCourier(Courier courier) { this.courier = courier; }

}