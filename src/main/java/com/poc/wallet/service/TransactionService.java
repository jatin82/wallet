package com.poc.wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.poc.wallet.exception.PlatformException;
import com.poc.wallet.model.TRANSACTION_TYPE;
import com.poc.wallet.model.db.Transaction;
import com.poc.wallet.model.db.User;
import com.poc.wallet.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	@Transactional
	@Rollback
	public Transaction executeTransaction(Transaction transaction) throws PlatformException {
		if(transaction.getType().equals(TRANSACTION_TYPE.ADDED)) {
			User recievingUser = userService.getUserByEmail(transaction.getRecievingUser().getEmail());

			transaction.setRecievingUserBalance(recievingUser.getBalance() + transaction.getMoney());
			recievingUser.setBalance(transaction.getRecievingUserBalance());
			transaction.setRecievingUser(recievingUser);
			
			userService.updateUser(recievingUser);
			transaction = transactionRepository.save(transaction);
			
		}
		else {
			User payingUser = userService.getUserByEmail(transaction.getPayingUser().getEmail());
			User recievingUser = userService.getUserByEmail(transaction.getRecievingUser().getEmail());
			
			if(payingUser.getBalance()-transaction.getMoney() >= 0) {
				transaction.setRecievingUserBalance(payingUser.getBalance() - transaction.getMoney());
				transaction.setPayingUserBalance(recievingUser.getBalance() + transaction.getMoney());
				payingUser.setBalance(transaction.getRecievingUserBalance());
				recievingUser.setBalance(transaction.getPayingUserBalance());
			
				transaction.setRecievingUser(payingUser);
				transaction.setPayingUser(recievingUser);
				
				userService.updateUser(recievingUser);
				userService.updateUser(payingUser);
				transaction = transactionRepository.save(transaction);
			}else {
				throw new PlatformException(HttpStatus.BAD_REQUEST, "Insufficient Balance");
			}
		}
		return transaction;
	}

	
	public List<Transaction> getPassbook(User user){
		return transactionRepository.findAll();
	}
}
