package com.ouedraogo_issaka.cafe_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ouedraogo_issaka.cafe_management_system.modeles.Facture;

public interface FactureRepository  extends JpaRepository <Facture, Integer> {
    
    List <Facture> getAllFactures();

    List <Facture> getFactureByUsername(@Param("username") String username);
}
