package com.poc.wallet.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.poc.wallet.model.TRANSACTION_TYPE;

public class TransactionDTO {

	
	@JsonProperty(required = true)
	private String payingUser;
	
	@JsonProperty(required = true)
	private long money;
	
	@JsonProperty(required = true)
	private TRANSACTION_TYPE type;

	
//	@JsonCreator
//	public TransactionDTO(String payingUser,long money,TRANSACTION_TYPE type) {
//		this.payingUser = payingUser;
//		this.money = money;
//		this.type = type;
//	}
	
	public String getPayingUser() {
		return payingUser;
	}

	public void setPayingUser(String payingUser) {
		this.payingUser = payingUser;
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
