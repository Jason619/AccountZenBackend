package com.group.AccountZen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.AccountZen.entity.Customer;
import com.group.AccountZen.repository.CustomerRepository;

/**
 * The CustomerService class provides business logic for customer-related operations.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Creates a new customer with the specified name.
     *
     * @param name The name of the customer to create.
     * @return The newly created customer.
     */
    public Customer createCustomer(String name) {
        Customer customer = new Customer();
        customer.setCustName(name);
        return customerRepository.save(customer);
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The customer if found, otherwise null.
     */
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}