package com.poc.wallet.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.wallet.exception.PlatformException;
import com.poc.wallet.model.ResponseData;
import com.poc.wallet.model.Token;
import com.poc.wallet.model.db.User;
import com.poc.wallet.model.dto.UserDTO;
import com.poc.wallet.service.AuthService;
import com.poc.wallet.util.Constants;

@RestController
public class AuthController {
	
	private static Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthService authService;
	
	@Autowired
	ModelMapper mapper;

	@PostMapping("/signin")
	public ResponseEntity<ResponseData<Token>> userSignIn(@RequestBody UserDTO userDTO) throws PlatformException {
		if(ObjectUtils.isEmpty(userDTO.getEmail()) || ObjectUtils.isEmpty(userDTO.getPassword())) {
			throw new PlatformException(HttpStatus.BAD_REQUEST,Constants.MISSING_DATA);
		}
		User user = new User();
		mapper.map(userDTO,user);
		Token token = authService.getAuthToken(user);
		ResponseData<Token> response = new ResponseData<>();
		response.setData(token);
		response.setMessage(Constants.SUCCESS);
		return new ResponseEntity<ResponseData<Token>>(response, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<ResponseData<String>> userSignUp(@RequestBody UserDTO userDTO) throws PlatformException {
		if(ObjectUtils.isEmpty(userDTO.getEmail()) || ObjectUtils.isEmpty(userDTO.getPassword())) {
			throw new PlatformException(HttpStatus.BAD_REQUEST,Constants.MISSING_DATA);
		}
		User user = new User();
		mapper.map(userDTO,user);
		authService.registerUser(user);
		ResponseData<String> response = new ResponseData<>();
		response.setMessage(Constants.SUCCESS);
		return new ResponseEntity<ResponseData<String>>(response, HttpStatus.CREATED);
	}

}
