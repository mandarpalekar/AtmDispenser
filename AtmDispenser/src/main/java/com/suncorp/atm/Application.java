package com.suncorp.atm;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.suncorp.atm.domain.Denom;
import com.suncorp.atm.service.DenominationService;

@SpringBootApplication
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer implements CommandLineRunner{
	
	@Autowired
	DenominationService denominationService;
	
	public static void main(String[] args) throws Exception{
		SpringApplication.run(Application.class, args);
	}
	
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


	@Override
    public void run(String... strings) throws Exception {
		Denom twenty = new Denom();
		twenty.setVal(BigInteger.valueOf(20));
		twenty.setName("20$ AUD");
		twenty.setTotal(BigInteger.valueOf(8));
		
		Denom fifty = new Denom();
		fifty.setVal(BigInteger.valueOf(50));
		fifty.setName("50$ AUD");
		fifty.setTotal(BigInteger.valueOf(3));
		
		denominationService.save(twenty);
		denominationService.save(fifty);
	}
}
