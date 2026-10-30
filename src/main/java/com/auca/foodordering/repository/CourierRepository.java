package com.auca.foodordering.repository;

import com.auca.foodordering.entity.Courier;
import com.auca.foodordering.entity.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {

    // C1 - JPQL: JOIN on the many-to-many collection + LOWER
    @Query("SELECT c FROM Courier c JOIN c.deliveryZones dz WHERE LOWER(dz.name) = LOWER(:name)")
    List<Courier> findCouriersByDeliveryZoneName(@Param("name") String name);

    // D4 - DERIVED: is any courier already using this motorbike?
    boolean existsByMotorbike(Motorbike motorbike);
}