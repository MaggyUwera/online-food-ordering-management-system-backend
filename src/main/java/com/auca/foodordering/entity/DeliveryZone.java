package com.auca.foodordering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "delivery_zone")
public class DeliveryZone { 

      @Id   
      @GeneratedValue(strategy = GenerationType.UUID)   
      private UUID id;  

      private String name;  

      @ManyToMany(mappedBy = "deliveryZones")   
      @JsonIgnore   
       private List<Courier> couriers;  
      
      
      public UUID getId() { return id; }   
      public void setId(UUID id) { this.id = id;}    
      
      public String getName() { return name; }
      public void setName(String name) { this.name = name; }

      public List<Courier> getCouriers() { return couriers;  }
      public void setCouriers(List<Courier> couriers) { this.couriers = couriers;  }

         }
        