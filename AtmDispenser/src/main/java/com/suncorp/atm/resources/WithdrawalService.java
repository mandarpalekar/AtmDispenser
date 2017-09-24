package com.suncorp.atm.resources;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suncorp.atm.domain.Denom;
import com.suncorp.atm.service.DenominationService;

@Service
public class WithdrawalService {
	
	@Autowired
	DenominationService denominationService;
	
	public Long totalAtmAmt = 0L;
	
	
	public String WithdrawCash(List<Denom> listOfDenominations, String amount) {
		
		Collections.sort(listOfDenominations, (s1, s2) -> Integer.compare(s2.getVal().intValue(), s1.getVal().intValue()));
		StringBuffer sb = new StringBuffer();
		List<Denom> listToUpdate = new ArrayList<Denom>();
		Map<Long,Long> mapOfValuesToUpdate = new HashMap<Long,Long>();
		Map<Long,Long> mapOfNextDenom = new HashMap<Long,Long>();
		Long prevId = 0L;
		for(Denom denom: listOfDenominations){			
        	totalAtmAmt = totalAtmAmt + (denom.getVal().longValue() * denom.getTotal().longValue());
        	if(listOfDenominations.indexOf(denom)==0){
        		prevId = denom.getId();
        	}else{
        		mapOfNextDenom.put(prevId, denom.getVal().longValue());
        		prevId = denom.getId();
        	}
        }
		
		Long amountLong = Long.parseLong(amount);
		Long originalAmount = amountLong;
	    if(amountLong <= totalAtmAmt){
	         for (Denom denom: listOfDenominations) {
	        	Denom updateDenom = new Denom();
	            Long currencyCount = 0L;
	            Long totalRemaining = 0L;
	            Long totalDenomInAtm = new Long(0L);	            
	            totalDenomInAtm  = denom.getTotal().longValue();
	            if (denom.getVal().longValue() <= amountLong) {
	            Long noteCount = amountLong / denom.getVal().longValue();	            
	            if(totalDenomInAtm > 0){
	            currencyCount = noteCount>=totalDenomInAtm?totalDenomInAtm:noteCount;
	            totalRemaining =  noteCount>=totalDenomInAtm?0:totalDenomInAtm - currencyCount;
	            //Deduct the total corpus left in the ATM Vault with the cash being dispensed in this iteration
	            totalAtmAmt = totalAtmAmt - (currencyCount * denom.getVal().longValue());
	            //Calculate the amount that need to be addressed in the next iterations
	            amountLong = amountLong -(currencyCount * denom.getVal().longValue());
	            if(amountLong >0 && (listOfDenominations.indexOf(denom) != listOfDenominations.size() - 1)){
	            while(currencyCount>0){
	            		if((amountLong % mapOfNextDenom.get(denom.getId()).longValue() !=0)){
	            			currencyCount = currencyCount -1;
	            			amountLong = originalAmount -(currencyCount * denom.getVal().longValue());
	            		}else{
	            			break;
	            		}
	            	}
	            }
	            if (currencyCount > 0){
	            	if((amountLong > 0) && (listOfDenominations.indexOf(denom) == listOfDenominations.size() - 1)){
		            	sb = new StringBuffer();
		            	sb.append("Sorry this amount cannot be withdrawen");
		            } else if((amountLong > 0) && (listOfDenominations.indexOf(denom) != listOfDenominations.size() - 1)){	            	
		            	updateDenom = denom;
		            	//updateDenom.setTotal(BigInteger.valueOf(updateDenom.getTotal().longValue() - currencyCount));
		            	mapOfValuesToUpdate.put(updateDenom.getId(), currencyCount);
		            	listToUpdate.add(updateDenom);
		            	sb.append(denom.getName() + " " + "X" + " "+ currencyCount + " " + "and ");
		            }else{
		            	sb.append(denom.getName() + " " + "X" + " "+ currencyCount + " notes dispensed");
		            	updateDenom = denom;
		            	//updateDenom.setTotal(BigInteger.valueOf(updateDenom.getTotal().longValue() - currencyCount));
		            	mapOfValuesToUpdate.put(updateDenom.getId(), currencyCount);
		            	listToUpdate.add(updateDenom);
		            }
	            } else {
	            	
	            }	            
	            
	         }                
	    }
	  }
	    if(amountLong == 0){
	 	   for(Denom denom : listToUpdate){
	 	    	Long valueToUpdate = mapOfValuesToUpdate.get(denom.getId());
	 	    	denom.setTotal(BigInteger.valueOf(denom.getTotal().longValue() - valueToUpdate));
	 		    denominationService.saveAndFlush(denom);
	 		}
	 	 }
	            
	}else{
		sb = new StringBuffer();
    	sb.append("Not enough cash in the ATM");
	}
	    
	    
		return sb.toString();
}

}
