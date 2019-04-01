package com.poc.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.wallet.model.db.User;
import com.poc.wallet.repository.UserRepository;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;
	
	public User getUserByEmail(String email) {
		log.debug("UserService fetch userByEmail");
		return repository.findByEmail(email);
	}
	
	public User getUserByUserId(int id) {
		log.debug("UserService fetch getUserByUserId");
		return repository.findByUserId(id);
	}
	
	public User updateUser(User user) {
		log.debug("UserService update updateUser : {}",user);
		return repository.save(user);
	}

}
