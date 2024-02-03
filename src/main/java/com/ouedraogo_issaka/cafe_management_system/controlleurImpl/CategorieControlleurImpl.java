package com.ouedraogo_issaka.cafe_management_system.controlleurImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ouedraogo_issaka.cafe_management_system.constants.CafeConstant;
import com.ouedraogo_issaka.cafe_management_system.controlleur.CategorieControlleur;
import com.ouedraogo_issaka.cafe_management_system.modeles.Categorie;
import com.ouedraogo_issaka.cafe_management_system.service.CategorieService;
import com.ouedraogo_issaka.cafe_management_system.utilaires.CafeUtilitaires;

@RestController
public class CategorieControlleurImpl implements CategorieControlleur {

    @Autowired
    CategorieService categorieService;

    @Override
    public ResponseEntity<String> saveCategorie(Map<String, String> requestMap) {
        try {
            return categorieService.saveCategorie(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Categorie>> findAllCategorie(String filterValue) {
        try {
            return categorieService.findAllCategorie(filterValue);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategorie(Map<String, String> requestMap) {
        try {
            return categorieService.updateCategorie(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
