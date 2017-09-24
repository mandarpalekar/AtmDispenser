package com.suncorp.atm.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.suncorp.atm.AbstractTest;
import com.suncorp.atm.domain.Denom;
import com.suncorp.atm.resources.WithdrawalService;

@Transactional
public class WithdrawalServiceTest extends AbstractTest {
	
	@Autowired
	private WithdrawalService withdrawalService;
	
	@Autowired
	private DenominationService denominationService;
	
	@Test
	public void TestWithDraw(){
		List<Denom> listOfDenominations = new ArrayList<Denom>();
		Denom twenty = denominationService.findOne(1L);		
		twenty.setTotal(BigInteger.valueOf(8));
		
		Denom fifty = denominationService.findOne(2L);
		fifty.setTotal(BigInteger.valueOf(3));
		
		listOfDenominations.add(twenty);
		listOfDenominations.add(fifty);
		
		String amountToWithdraw = "200";
		String sb = withdrawalService.WithdrawCash(listOfDenominations, amountToWithdraw);
		
		assertEquals("failure - expected balance for 50 dollars, ", BigInteger.valueOf(1L), fifty.getTotal());
		assertEquals("failure - expected balance for 20 dollars, ", BigInteger.valueOf(3L), twenty.getTotal());
		System.out.println("hi");
	}
	
	@Test
	public void TestWithDrawWithLessAvailableCash(){
		List<Denom> listOfDenominations = new ArrayList<Denom>();
		Denom twenty = denominationService.findOne(1L);		
		twenty.setTotal(BigInteger.valueOf(8));
		
		Denom fifty = denominationService.findOne(2L);
		fifty.setTotal(BigInteger.valueOf(3));
		
		listOfDenominations.add(twenty);
		listOfDenominations.add(fifty);
		
		String amountToWithdraw = "600";
		String expectedMsg = "Not enough cash in the ATM";
		String sb = withdrawalService.WithdrawCash(listOfDenominations, amountToWithdraw);
		
		assertEquals("failure - expected expected message ", expectedMsg, sb.toString());		
		System.out.println("hi");
	}

}
