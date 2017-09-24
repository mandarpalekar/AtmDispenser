package com.suncorp.atm.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suncorp.atm.domain.Denom;

@Repository
public interface DenominationService extends JpaRepository<Denom, Long> {

}
