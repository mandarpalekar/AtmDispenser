package com.suncorp.atm.domain;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Transaction")
public class Transaction {	
	
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	private BigInteger amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
}
