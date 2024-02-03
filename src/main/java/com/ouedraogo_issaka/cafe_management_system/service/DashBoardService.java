package com.ouedraogo_issaka.cafe_management_system.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface DashBoardService {
    ResponseEntity<Map<String, Object>> getCount();
}
