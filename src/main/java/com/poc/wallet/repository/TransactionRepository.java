package com.poc.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.wallet.model.db.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
