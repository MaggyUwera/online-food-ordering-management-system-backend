package com.auca.foodordering.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer {   

    @Id   
    @GeneratedValue(strategy = GenerationType.UUID)    
    private UUID id;

    private String firstName; 

    private String lastName;

    @Column(unique = true)    
    private String phone;

    private String deliveryAddress; 

    public UUID getId() { return id; } 
    public void setId(UUID id) { this.id = id; } 

    public String getFirstName() { return firstName; }  
    public void setFirstName(String firstName) { this.firstName = firstName; }

             
    public String getLastName() { return lastName; }   
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }   
    public void setPhone(String phone) { this.phone = phone; } 

    public String getDeliveryAddress() { return deliveryAddress; }    
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
            
}