package com.auca.foodordering.service;

import com.auca.foodordering.entity.Courier;
import com.auca.foodordering.entity.DeliveryZone;
import com.auca.foodordering.repository.CourierRepository;
import com.auca.foodordering.repository.DeliveryZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourierService {

    private final CourierRepository courierRepository;
    private final DeliveryZoneRepository deliveryZoneRepository;

    public CourierService(CourierRepository courierRepository,
                          DeliveryZoneRepository deliveryZoneRepository) {
        this.courierRepository = courierRepository;
        this.deliveryZoneRepository = deliveryZoneRepository;
    }

    public String saveCourier(Courier courier) {
        courierRepository.save(courier);
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

    public String deleteCourier(UUID id) {
        if (!courierRepository.existsById(id)) {
            return "The courier with that id does not exist";
        }
        courierRepository.deleteById(id);
        return "Courier deleted successfully";
    }

    // C1 - couriers who hold a delivery zone (name matched ignoring case)
    public List<Courier> getCouriersByDeliveryZone(String name) {
        return courierRepository.findCouriersByDeliveryZoneName(name);
    }

    // D3 - add one delivery zone to a courier, keeping the existing ones
    public String addDeliveryZone(UUID courierId, UUID deliveryZoneId) {
        Optional<Courier> courierOptional = courierRepository.findById(courierId);
        if (courierOptional.isEmpty()) {
            return "The courier with that id does not exist";
        }

        Optional<DeliveryZone> zoneOptional = deliveryZoneRepository.findById(deliveryZoneId);
        if (zoneOptional.isEmpty()) {
            return "That delivery zone does not exist";
        }

        Courier courier = courierOptional.get();
        DeliveryZone deliveryZone = zoneOptional.get();

        if (courier.getDeliveryZones().contains(deliveryZone)) {
            return "The courier already has that delivery zone";
        }

        courier.getDeliveryZones().add(deliveryZone); // keep the old zones, add the new one
        courierRepository.save(courier);              // save the owner (Courier has the @JoinTable)
        return "Delivery zone added to the courier";
    }
}