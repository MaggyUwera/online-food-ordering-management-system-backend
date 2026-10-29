package com.auca.foodordering.service;

import com.auca.foodordering.entity.Customer;
import com.auca.foodordering.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String save(Customer customer) {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> update(UUID id, Customer customer) {
        Optional<Customer> existing = customerRepository.findById(id);
        if (existing.isEmpty()) return Optional.empty();

        existing.get().setFirstName(customer.getFirstName());
        existing.get().setLastName(customer.getLastName());
        existing.get().setPhone(customer.getPhone());
        existing.get().setDeliveryAddress(customer.getDeliveryAddress());

        return Optional.of(customerRepository.save(existing.get()));
    }

    public String delete(UUID id) {
        Optional<Customer> existing = customerRepository.findById(id);
        if (existing.isEmpty()) return "The customer with that id does not exist";

        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }

    // B1 - customers by lastName (DERIVED: IgnoreCase + OrderBy)
    public List<Customer> getCustomersByLastName(String lastName) {
        return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // C4 - customers who have at least one meal order with this courier (JPQL)
    public List<Customer> getCustomersOfCourier(UUID courierId) {
        return customerRepository.findCustomersOfCourier(courierId);
    }
}