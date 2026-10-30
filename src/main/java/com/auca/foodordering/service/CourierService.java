package com.auca.foodordering.service;

import com.auca.foodordering.entity.Courier;
import com.auca.foodordering.entity.DeliveryZone;
import com.auca.foodordering.entity.Motorbike;
import com.auca.foodordering.repository.CourierRepository;
import com.auca.foodordering.repository.DeliveryZoneRepository;
import com.auca.foodordering.repository.MotorbikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourierService {

    private final CourierRepository courierRepository;
    private final DeliveryZoneRepository deliveryZoneRepository;
    private final MotorbikeRepository motorbikeRepository;

    public CourierService(CourierRepository courierRepository,
                          DeliveryZoneRepository deliveryZoneRepository,
                          MotorbikeRepository motorbikeRepository) {
        this.courierRepository = courierRepository;
        this.deliveryZoneRepository = deliveryZoneRepository;
        this.motorbikeRepository = motorbikeRepository;
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

        courier.getDeliveryZones().add(deliveryZone);
        courierRepository.save(courier);
        return "Delivery zone added to the courier";
    }

    // D4 - give the courier another motorbike; the old one becomes free
    public String changeMotorbike(UUID courierId, int bikeNumber) {
        Optional<Courier> courierOptional = courierRepository.findById(courierId);
        if (courierOptional.isEmpty()) {
            return "The courier with that id does not exist";
        }

        Optional<Motorbike> motorbikeOptional = motorbikeRepository.findByBikeNumber(bikeNumber);
        if (motorbikeOptional.isEmpty()) {
            return "The motorbike with that bikeNumber does not exist";
        }

        Courier courier = courierOptional.get();
        Motorbike motorbike = motorbikeOptional.get();

        boolean alreadyHisOwn = courier.getMotorbike() != null
                && courier.getMotorbike().getId().equals(motorbike.getId());

        if (courierRepository.existsByMotorbike(motorbike) && !alreadyHisOwn) {
            return "That motorbike is taken by another courier";
        }

        courier.setMotorbike(motorbike);
        courierRepository.save(courier);
        return "Motorbike changed successfully";
    }
}