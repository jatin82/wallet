package com.poc.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.wallet.model.db.User;
import com.poc.wallet.repository.UserRepository;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository repository;
	
	public User getUserByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public User getUserByUserId(int id) {
		return repository.findByUserId(id);
	}
	
	public User updateUser(User user) {
		return repository.save(user);
	}

}
