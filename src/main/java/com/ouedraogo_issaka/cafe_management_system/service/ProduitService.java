package com.ouedraogo_issaka.cafe_management_system.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ouedraogo_issaka.cafe_management_system.dto.ProduitDto;

public interface ProduitService {
    
    ResponseEntity<String> saveProduit(Map<String, String> requestMap);

    ResponseEntity<List<ProduitDto>> findAllProduit();

    ResponseEntity<String> updateProduit(Map<String, String> requestMap);

    ResponseEntity<String> deleteProduit(Integer id);

    ResponseEntity<String> updateStatutProduit(Map<String, String> requestMap);

    ResponseEntity<List<ProduitDto>> findProduitByCategorie(Integer id);

    ResponseEntity<List<ProduitDto>> findProduitById(Integer id);
}
