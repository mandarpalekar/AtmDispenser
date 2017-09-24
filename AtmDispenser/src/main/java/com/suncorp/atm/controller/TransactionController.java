package com.suncorp.atm.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suncorp.atm.domain.Denom;
import com.suncorp.atm.domain.Transaction;
import com.suncorp.atm.resources.WithdrawalService;
import com.suncorp.atm.service.DenominationService;
import com.suncorp.atm.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	private DenominationService denominationService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired 
	private WithdrawalService withdrawalService;
	
	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String transaction(Model model){
		model.addAttribute("denominations", denominationService.findAll());
		return "/transactions";
	}
	
	@RequestMapping(value="/withdraw", method = RequestMethod.POST)
    @ResponseBody
    public String saveWithDrawal(@RequestParam String amount){
		List<Denom> listOfDenominations = new ArrayList<Denom>();
		listOfDenominations = denominationService.findAll();
		denominationService.flush();
		String sb = withdrawalService.WithdrawCash(listOfDenominations,amount);
		Transaction transaction = new Transaction();
		transaction.setAmount(BigInteger.valueOf(Long.parseLong(amount)));
		transactionService.save(transaction);
		return sb;
	}
	
}
