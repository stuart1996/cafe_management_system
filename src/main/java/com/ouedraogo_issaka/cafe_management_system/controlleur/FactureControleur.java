package com.ouedraogo_issaka.cafe_management_system.controlleur;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ouedraogo_issaka.cafe_management_system.modeles.Facture;

@RequestMapping(path = "/facture")
public interface FactureControleur {
    
    @PostMapping(path = "/generedRapport")
    ResponseEntity<String> generedRapport (@RequestBody(required=true) Map<String, Object> requestMap);

    @GetMapping(path = "/all")
    ResponseEntity<List<Facture>> findAllFacture ();

    @PostMapping(path = "/addPdf")
    ResponseEntity<byte[]> addPdf (@RequestBody Map<String, Object> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteFacture (@PathVariable Integer id);

}