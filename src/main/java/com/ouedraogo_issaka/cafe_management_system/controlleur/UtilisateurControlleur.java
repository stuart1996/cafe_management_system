package com.ouedraogo_issaka.cafe_management_system.controlleur;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ouedraogo_issaka.cafe_management_system.dto.UtilisateurDto;

@RequestMapping(path = "/utilisateur")
public interface UtilisateurControlleur {
    
    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp (@RequestBody(required=true) Map<String, String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity<String> login (@RequestBody(required=true) Map<String, String> requestMap);

    @GetMapping(path = "/all")
    public ResponseEntity<List<UtilisateurDto>> findAllUtilisateur ();

    @PutMapping(path = "/update")
    public ResponseEntity<String> updateUtilisateur (@RequestBody(required=true) Map<String, String> requestMap);

    @GetMapping(path = "/verifierToken")
    public ResponseEntity<String> verifierToken ();

    @PutMapping(path = "/changerPassword")
    public ResponseEntity<String> changerPassword (@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/passwordOublie")
    public ResponseEntity<String> passwordOublie (@RequestBody Map<String, String> requestMap);
}