package com.group.AccountZen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.AccountZen.dto.CreateCustomerRequest;
import com.group.AccountZen.dto.CustomerOperationRequest;
import com.group.AccountZen.entity.Customer;
import com.group.AccountZen.service.CustomerService;

/**
 * The CustomerController class handles RESTful API endpoints related to customer management.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Creates a new customer.
     *
     * @param createCustomerRequest The request object containing the customer name.
     * @return ResponseEntity containing the created customer details or an error message.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerService.createCustomer(createCustomerRequest.getCustName());
        if (customer != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create customer");
        }
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerOperationRequest The request object containing the customer ID.
     * @return ResponseEntity containing the customer details if found or an error message.
     */
    @PostMapping("/get")
    public ResponseEntity<?> getCustomerById(@RequestBody CustomerOperationRequest customerOperationRequest) {
        Customer customer = customerService.getCustomerById(customerOperationRequest.getCustId());
        if (customer != null) {
            return ResponseEntity.ok().body(customer);
        } else {
        	return ResponseEntity.badRequest().body("Customer Id Invalid. Please enter a valid Customer ID.");
        }
    }

}