package com.ouedraogo_issaka.cafe_management_system.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface FactureService {
    
    ResponseEntity<String> generedRapport(Map<String, Object> requestMap);

    ResponseEntity<byte[]> addPdf(Map<String, Object> requestMap);

    ResponseEntity<String> deleteFacture(Integer id);
}
