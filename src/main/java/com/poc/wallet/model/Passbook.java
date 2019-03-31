package com.poc.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class Passbook {

	
	private String payingUser;
	
	private String recievingUser;
	
	private long balance;
	
	private TRANSACTION_TYPE type;
	
	private long money;

	public String getPayingUser() {
		return payingUser;
	}

	public void setPayingUser(String payingUser) {
		this.payingUser = payingUser;
	}

	public String getRecievingUser() {
		return recievingUser;
	}

	public void setRecievingUser(String recievingUser) {
		this.recievingUser = recievingUser;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public TRANSACTION_TYPE getType() {
		return type;
	}

	public void setType(TRANSACTION_TYPE type) {
		this.type = type;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}
	
}
