package com.suncorp.atm.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.suncorp.atm.AbstractTest;
import com.suncorp.atm.domain.Denom;

@Transactional
public class DenominationServiceTest extends AbstractTest {
	
	@Autowired
	private DenominationService denominationService;
	
	
	@Before
	public void setUp(){
		
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void TestFindAll(){
		List<Denom> listOfDenominations = new ArrayList<Denom>();
		listOfDenominations = denominationService.findAll();
		
		assertNotNull("failure - expected not null", listOfDenominations);
		assertEquals("failure - expected size, ", 2, listOfDenominations.size());
	}
	
	@Test
	public void TestFindOne(){
		Long id = new Long(1);
		Denom denom = denominationService.findOne(id);
		
		assertNotNull("failure - expected not null", denom);
		assertEquals("failure - expected denomination id attribute match, ", id, denom.getId());
	}
	
	@Test
	public void TestUpdateDenom(){
		Long id = new Long(1);
		Denom denom = denominationService.findOne(id);
		
		assertNotNull("failure - expected not null", denom);
		
		BigInteger updatedTotal = BigInteger.valueOf(8);
		denom.setTotal(updatedTotal);
		denominationService.saveAndFlush(denom);
		
		assertNotNull("failure - expected updated denomination not null", denom);
		assertEquals("failure - expected updated denomination id attribute unchanged, ", id, denom.getId());
		assertEquals("failure - expected updated denomination total match, ", updatedTotal, denom.getTotal());
		
	}

}
