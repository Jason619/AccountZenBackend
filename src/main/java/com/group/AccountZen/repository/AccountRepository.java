package com.group.AccountZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.AccountZen.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}