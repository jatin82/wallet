package com.poc.wallet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poc.wallet.exception.PlatformException;
import com.poc.wallet.model.Passbook;
import com.poc.wallet.model.TRANSACTION_TYPE;
import com.poc.wallet.model.db.Transaction;
import com.poc.wallet.model.db.User;
import com.poc.wallet.repository.TransactionRepository;

@Service
public class TransactionService {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Transactional
	public synchronized Transaction executeTransaction(Transaction transaction) throws PlatformException {
		log.debug("executeTransaction with {}",transaction);
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
			
				transaction.setRecievingUser(recievingUser);
				transaction.setPayingUser(payingUser);
				transaction.setTransactionDate(new Date());
				
				userService.updateUser(recievingUser);
				userService.updateUser(payingUser);
				transaction = transactionRepository.save(transaction);
			}else {
				log.error("Insufficient Balance for payingUser : {}",payingUser);
				throw new PlatformException(HttpStatus.BAD_REQUEST, "Insufficient Balance");
			}
		}
		return transaction;
	}

	
	public List<Passbook> getPassbook(User user){

		log.debug("TransactionService getPassbook for user {}",user);
		List<Transaction> transactions = transactionRepository.findByRecievingUserOrPayingUserOrderByTransactionDateAsc(user,user);
		
		List<Passbook> passbooks = new ArrayList<>();
		transactions.forEach(t->{
			Passbook passbook = new Passbook();
			if(t.getType().equals(TRANSACTION_TYPE.ADDED)) {
				passbook.setType(t.getType());
				passbook.setRecievingUser(user.getEmail());
				passbook.setBalance(t.getRecievingUserBalance());
				passbook.setMoney(t.getMoney());
			}
			else {
				passbook.setType(t.getType());
				passbook.setRecievingUser(t.getRecievingUser().getEmail());
				passbook.setPayingUser(t.getPayingUser().getEmail());
				passbook.setMoney(t.getMoney());
				if(t.getPayingUser().equals(user.getEmail())) {
					passbook.setBalance(t.getPayingUserBalance());
				}
				else {
					passbook.setBalance(t.getRecievingUserBalance());
				}
			}
			passbooks.add(passbook);
		});
		log.debug("TransactionService getPassbook fetched all passbooks {}",passbooks);
		return passbooks;
	}
}
