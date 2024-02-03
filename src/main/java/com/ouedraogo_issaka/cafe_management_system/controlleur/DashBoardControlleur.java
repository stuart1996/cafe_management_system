package com.ouedraogo_issaka.cafe_management_system.controlleur;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/dashboard")
public interface DashBoardControlleur {

    @GetMapping(path = "/details")
    ResponseEntity <Map<String, Object>> getCount();   
}
