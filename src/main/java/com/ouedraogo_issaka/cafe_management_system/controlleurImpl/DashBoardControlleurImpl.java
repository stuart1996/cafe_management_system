package com.ouedraogo_issaka.cafe_management_system.controlleurImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ouedraogo_issaka.cafe_management_system.controlleur.DashBoardControlleur;
import com.ouedraogo_issaka.cafe_management_system.service.DashBoardService;

@RestController
public class DashBoardControlleurImpl implements DashBoardControlleur{
    
    @Autowired
    DashBoardService dashBoardService;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        return dashBoardService.getCount();
    }

    
}
