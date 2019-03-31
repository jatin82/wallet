package com.poc.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.wallet.model.db.Transaction;
import com.poc.wallet.model.db.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	
	public List<Transaction> findByRecievingUserOrPayingUserOrderByTransactionDateAsc(User recievingUser,User payingUser);
}
