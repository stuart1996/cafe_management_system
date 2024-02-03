package com.ouedraogo_issaka.cafe_management_system.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ouedraogo_issaka.cafe_management_system.dto.UtilisateurDto;

public interface UtilisateurService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<List<UtilisateurDto>> findAllUtilisateur();

    ResponseEntity<String> updateUtilisateur (Map<String, String> requestMap);

    ResponseEntity<String> verifierToken();

    ResponseEntity<String> changerPassword(Map<String, String> requestMap);

    ResponseEntity<String> passwordOublie(Map<String, String> requestMap);
}