package com.auca.foodordering.repository;

import com.auca.foodordering.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> { 
       // B1 - DERIVED: IgnoreCase + OrderBy   
        List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
            // C4 - JPQL: DISTINCT + path navigation   
        @Query("SELECT DISTINCT mo.customer FROM MealOrder mo WHERE mo.courier.id = :courierId")   
        List<Customer> findCustomersOfCourier(@Param("courierId") UUID courierId);
    }