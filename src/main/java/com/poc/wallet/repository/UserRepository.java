package com.poc.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.wallet.model.db.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	

	public User findByEmail(String email);
	
	public User findByUserId(int id);
	
}
