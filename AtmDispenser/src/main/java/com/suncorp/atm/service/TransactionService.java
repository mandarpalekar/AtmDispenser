package com.suncorp.atm.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suncorp.atm.domain.Transaction;

@Repository
public interface TransactionService extends JpaRepository<Transaction, Long> {

}
