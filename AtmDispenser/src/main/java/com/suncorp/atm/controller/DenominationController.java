package com.suncorp.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suncorp.atm.domain.Denom;
import com.suncorp.atm.service.DenominationService;

@Controller
public class DenominationController {

	@Autowired
	private DenominationService denominationService;
	
	@RequestMapping("/denominations/{id}")
	public String denomination(@PathVariable Long id, Model model){
		model.addAttribute("denominations", denominationService.findOne(id));
		return "denominations";
	}
	
	@RequestMapping(value = "/denominations", method = RequestMethod.GET)
	public String denominationsList(Model model){
		model.addAttribute("denominations", denominationService.findAll());
		return "denominations";
	}
	
	@RequestMapping(value = "/savedenomination", method = RequestMethod.PUT)
	@ResponseBody
	public Long saveDenominations(@RequestBody Denom denominations ){
		Long id = Long.parseLong(denominations.getName());
		Denom denomination = denominationService.findOne(id);
		denomination.setTotal(denomination.getTotal().add(denominations.getTotal()));
		denominationService.saveAndFlush(denomination);
        return denomination.getId();
	}
	
}
