package com.poc.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.poc.wallet.exception.PlatformException;
import com.poc.wallet.model.ResponseData;
import com.poc.wallet.model.Token;
import com.poc.wallet.model.db.User;
import com.poc.wallet.repository.UserRepository;
import com.poc.wallet.util.Constants;

@Service
public class AuthService {

	@Autowired
	UserService userService;

	@Autowired
	JWTService jwtService;

	public Token getAuthToken(User user) throws PlatformException {
		User userfound = userService.getUserByEmail(user.getEmail());
		if (!ObjectUtils.isEmpty(userfound) && userfound.getPassword().equals(user.getPassword())) {
			return jwtService.generateAuthToken(userfound, Constants.JWTTOKEN);
		}
		throw new PlatformException(HttpStatus.NOT_FOUND, "User not registered or invalid credentials");
	}

	public User registerUser(User user) throws PlatformException {
		User userfound = userService.getUserByEmail(user.getEmail());
		if (ObjectUtils.isEmpty(userfound)) {
			user.setBalance(0L);
			User userCreated = userService.updateUser(user);
			if (ObjectUtils.isEmpty(userCreated)) {
				throw new PlatformException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Unable to register users");
			}
			userCreated.setPassword(null);
			return userCreated;
		}
		throw new PlatformException(HttpStatus.ALREADY_REPORTED, "User already registered");

	}

}
