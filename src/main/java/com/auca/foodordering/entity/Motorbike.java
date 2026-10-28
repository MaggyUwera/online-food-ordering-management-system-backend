package com.auca.foodordering.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "motorbike")
public class Motorbike {    
    
    @Id   
    @GeneratedValue(strategy = GenerationType.UUID)   
    private UUID id;

    private int bikeNumber;    
    
    private String plateNumber;

    public UUID getId() { return id; }    
    public void setId(UUID id) { this.id = id; }  

          
    public int getBikeNumber() { return bikeNumber; }    
    public void setBikeNumber(int bikeNumber) { this.bikeNumber = bikeNumber; } 

    public String getPlateNumber() { return plateNumber; }   
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
        
    
    }