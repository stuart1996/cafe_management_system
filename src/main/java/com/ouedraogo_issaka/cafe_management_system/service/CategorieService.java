package com.ouedraogo_issaka.cafe_management_system.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ouedraogo_issaka.cafe_management_system.modeles.Categorie;

public interface CategorieService {
    
    ResponseEntity<String> saveCategorie(Map<String, String> requestMap);

    ResponseEntity<List<Categorie>> findAllCategorie(String filterValue);

    ResponseEntity<String> updateCategorie(Map<String, String> requestMap);
}
