package com.poc.wallet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.wallet.exception.PlatformException;
import com.poc.wallet.model.Passbook;
import com.poc.wallet.model.ResponseData;
import com.poc.wallet.model.TRANSACTION_TYPE;
import com.poc.wallet.model.db.Transaction;
import com.poc.wallet.model.db.User;
import com.poc.wallet.model.dto.TransactionDTO;
import com.poc.wallet.model.dto.UserDTO;
import com.poc.wallet.service.TransactionService;
import com.poc.wallet.service.UserService;
import com.poc.wallet.util.Constants;

@RestController
@RequestMapping("/user")
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);

	private User user;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	ModelMapper mapper;
	
	private void preProcessRequest(HttpServletRequest request){
		user = (User)request.getAttribute(Constants.USER);
	}
	
	private Transaction processTransaction(TransactionDTO transactionDTO) throws PlatformException {
		Transaction transaction = null;
		if(transactionDTO.getType().equals(TRANSACTION_TYPE.ADDED) && transactionDTO.getUser().equals(user.getEmail())) {
			transaction = new Transaction();
			User recievingUser = new User();
			recievingUser.setEmail(user.getEmail());
			transaction.setRecievingUser(recievingUser);
			transaction.setMoney(transactionDTO.getMoney());
			transaction.setType(transactionDTO.getType());
			return transaction;
		}
		else if(transactionDTO.getType().equals(TRANSACTION_TYPE.TRANSFERED) && !transactionDTO.getUser().equals(user.getEmail())) {
			transaction = new Transaction();
			User payingUser = new User();
			User recievingUser = new User();
			
			payingUser.setEmail(user.getEmail());
			recievingUser.setEmail(transactionDTO.getUser());
			
			transaction.setPayingUser(payingUser);
			transaction.setRecievingUser(recievingUser);
			transaction.setMoney(transactionDTO.getMoney());
			transaction.setType(transactionDTO.getType());
			return transaction;
		}
		throw new PlatformException(HttpStatus.BAD_REQUEST, "Data Incorrect");
	}
	
	
	@GetMapping
	public ResponseEntity<ResponseData<User>> getUser(HttpServletRequest request){
		preProcessRequest(request);
		user = userService.getUserByUserId(user.getUserId());
		ResponseData<User> response = new ResponseData<>();
		response.setData(user);
		response.setMessage(Constants.SUCCESS);
		return new ResponseEntity<ResponseData<User>>(response,HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<ResponseData<User>> updateUser(HttpServletRequest request,@RequestBody UserDTO updateUser){
		preProcessRequest(request);
		User userMap = new User();
		mapper.map(updateUser, userMap);
		
		// email and id cannot be changed
		userMap.setUserId(user.getUserId());
		userMap.setEmail(user.getEmail());
		
		userMap = userService.updateUser(userMap);
		
		ResponseData<User> response = new ResponseData<>();
		response.setData(userMap);
		response.setMessage(Constants.SUCCESS);
		return new ResponseEntity<ResponseData<User>>(response,HttpStatus.CREATED);
	}
	
	@PostMapping("/transaction")
	public ResponseEntity<ResponseData<String>> transferMoney(HttpServletRequest request,@RequestBody TransactionDTO transactionDTO) throws PlatformException{
		preProcessRequest(request);
		Transaction transaction = processTransaction(transactionDTO);
		transaction = transactionService.executeTransaction(transaction);
		ResponseData<String> response = new ResponseData<>();
		response.setMessage(Constants.SUCCESS);
		return new ResponseEntity<ResponseData<String>>(response,HttpStatus.OK);

	}
	
	@GetMapping("/passbook")
	public ResponseEntity<ResponseData<List<Passbook>>> getAllTransactions(HttpServletRequest request){
		preProcessRequest(request);
		List<Passbook> passbooks = transactionService.getPassbook(user);
		ResponseData<List<Passbook>> response = new ResponseData<>();
		response.setData(passbooks);
		response.setMessage(Constants.SUCCESS);
		return new ResponseEntity<ResponseData<List<Passbook>>>(response,HttpStatus.OK);
	}
	
}
