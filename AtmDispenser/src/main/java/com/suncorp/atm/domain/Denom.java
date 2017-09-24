package com.suncorp.atm.domain;

import java.math.BigInteger;
import javax.persistence.*;

@Entity(name="Denom")
public class Denom {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    
    private BigInteger val;

    private String name;

    private BigInteger total;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public BigInteger getVal() {
		return val;
	}

	public void setVal(BigInteger val) {
		this.val = val;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}
}

