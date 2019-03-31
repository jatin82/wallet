package com.poc.wallet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poc.wallet.model.TRANSACTION_TYPE;

public class TransactionDTO {

	
	@JsonProperty(required = true)
	private String user;
	
	@JsonProperty(required = true)
	private long money;
	
	@JsonProperty(required = true)
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
