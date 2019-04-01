package com.poc.wallet.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.poc.wallet.model.TRANSACTION_TYPE;

public class TransactionDTO {

	
	@NotBlank
	@NotNull
	private String user;
	
	@NotBlank
	@NotNull
	private long money;
	
	@NotBlank
	private TRANSACTION_TYPE type;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public TRANSACTION_TYPE getType() {
		return type;
	}

	public void setType(TRANSACTION_TYPE type) {
		this.type = type;
	}

	
}
