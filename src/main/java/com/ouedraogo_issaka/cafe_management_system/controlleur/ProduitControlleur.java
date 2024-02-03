package com.ouedraogo_issaka.cafe_management_system.controlleur;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ouedraogo_issaka.cafe_management_system.dto.ProduitDto;

@RequestMapping(path = "/produit")
public interface ProduitControlleur {
    
    @PostMapping(path = "/create")
    public ResponseEntity<String> saveProduit (@RequestBody(required=true) Map<String, String> requestMap);

    @GetMapping(path = "/all")
    public ResponseEntity<List<ProduitDto>> findAllProduit ();

    @PutMapping(path = "/update")
    public ResponseEntity<String> updateProduit (@RequestBody(required=true) Map<String, String> requestMap);

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProduit (@PathVariable(required=true) Integer id);

    @PutMapping(path = "/update/status")
    public ResponseEntity<String> updateStatutProduit (@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/filter/categorie/{id}")
    public ResponseEntity<List<ProduitDto>> findProduitByCategorie (@PathVariable(required=true) Integer id);

    @GetMapping(path = "/filter/{id}")
    public ResponseEntity<List<ProduitDto>> findProduitById (@PathVariable(required=true) Integer id);
}