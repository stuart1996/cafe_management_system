package com.ouedraogo_issaka.cafe_management_system.controlleurImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ouedraogo_issaka.cafe_management_system.constants.CafeConstant;
import com.ouedraogo_issaka.cafe_management_system.controlleur.ProduitControlleur;
import com.ouedraogo_issaka.cafe_management_system.dto.ProduitDto;
import com.ouedraogo_issaka.cafe_management_system.service.ProduitService;
import com.ouedraogo_issaka.cafe_management_system.utilaires.CafeUtilitaires;

@RestController
public class ProduitControlleurImpl implements ProduitControlleur {

    @Autowired
    ProduitService produitService;

    @Override
    public ResponseEntity<String> saveProduit(Map<String, String> requestMap) {
        try {
            return produitService.saveProduit(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProduitDto>> findAllProduit() {
        try {
            return produitService.findAllProduit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduit(Map<String, String> requestMap) {
        try {
            return produitService.updateProduit(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduit(Integer id) {
        try {
            return produitService.deleteProduit(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatutProduit(Map<String, String> requestMap) {
        try {
            return produitService.updateStatutProduit(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProduitDto>> findProduitByCategorie(Integer id) {
        try {
            return produitService.findProduitByCategorie(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<ProduitDto>> findProduitById(Integer id) {
        try {
            return produitService.findProduitById(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
