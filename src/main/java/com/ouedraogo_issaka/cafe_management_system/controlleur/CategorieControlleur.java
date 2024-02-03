package com.ouedraogo_issaka.cafe_management_system.controlleur;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouedraogo_issaka.cafe_management_system.modeles.Categorie;

@RequestMapping(path = "/categorie")
public interface CategorieControlleur {
    
    @PostMapping(path = "/create")
    public ResponseEntity<String> saveCategorie (@RequestBody(required=true) Map<String, String> requestMap);

    @GetMapping(path = "/all")
    public ResponseEntity<List<Categorie>> findAllCategorie (@RequestParam(required=false)  String filterValue);

    @PostMapping(path = "/update")
    public ResponseEntity<String> updateCategorie (@RequestBody(required=true) Map<String, String> requestMap);
}
