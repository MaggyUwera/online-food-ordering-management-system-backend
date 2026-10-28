package com.auca.foodordering.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "courier")
public class Courier {  
    
    @Id   
    @GeneratedValue(strategy = GenerationType.UUID)   
    private UUID id; 

    private String firstName; 

    private String lastName; 

    private String phone;  

    @ManyToOne   
    @JoinColumn(name = "motorbike_id") 
    private Motorbike motorbike; 

    @ManyToMany   
    @JoinTable( 
           name = "courier_delivery_zone",           
           joinColumns = @JoinColumn(name = "courier_id"),           
           inverseJoinColumns = @JoinColumn(name = "delivery_zone_id")   
 )    
    private List<DeliveryZone> deliveryZones; 

    public UUID getId() { return id; }    
    public void setId(UUID id) { this.id = id; } 

                   
    public String getFirstName() { return firstName; }   
    public void setFirstName(String firstName) { this.firstName = firstName; }  

    public String getLastName() { return lastName; }   
    public void setLastName(String lastName) { this.lastName = lastName; } 

    public String getPhone() { return phone; }    
    public void setPhone(String phone) { this.phone = phone; }  

    public Motorbike getMotorbike() { return motorbike; }  
    public void setMotorbike(Motorbike motorbike) { this.motorbike = motorbike; }  

    public List<DeliveryZone> getDeliveryZones() { return deliveryZones; }   
    public void setDeliveryZones(List<DeliveryZone> deliveryZones) { this.deliveryZones = deliveryZones; }
}