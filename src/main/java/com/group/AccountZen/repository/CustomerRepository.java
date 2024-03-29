package com.group.AccountZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.AccountZen.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}