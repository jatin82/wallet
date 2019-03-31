package com.poc.wallet.model.db;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poc.wallet.model.TRANSACTION_TYPE;


@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private long trasactionId; 
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne( cascade= {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name="payingUser", nullable = true)
	private User payingUser;
	
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne( cascade= {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name="recievingUser", nullable = false)
	private User recievingUser;
	
	private long money;
	
	private Date transactionDate;
	
	@Column(nullable = true)
	private long payingUserBalance;
	
	private long recievingUserBalance;
	
	private TRANSACTION_TYPE type;

	public long getTrasactionId() {
		return trasactionId;
	}

	public void setTrasactionId(long trasactionId) {
		this.trasactionId = trasactionId;
	}

	public User getPayingUser() {
		return payingUser;
	}

	public void setPayingUser(User payingUser) {
		this.payingUser = payingUser;
	}

	public User getRecievingUser() {
		return recievingUser;
	}

	public void setRecievingUser(User recievingUser) {
		this.recievingUser = recievingUser;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getPayingUserBalance() {
		return payingUserBalance;
	}

	public void setPayingUserBalance(long payingUserBalance) {
		this.payingUserBalance = payingUserBalance;
	}

	public long getRecievingUserBalance() {
		return recievingUserBalance;
	}

	public void setRecievingUserBalance(long recievingUserBalance) {
		this.recievingUserBalance = recievingUserBalance;
	}

	public TRANSACTION_TYPE getType() {
		return type;
	}

	public void setType(TRANSACTION_TYPE type) {
		this.type = type;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
