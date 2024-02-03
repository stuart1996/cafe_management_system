package com.ouedraogo_issaka.cafe_management_system.controlleurImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ouedraogo_issaka.cafe_management_system.constants.CafeConstant;
import com.ouedraogo_issaka.cafe_management_system.controlleur.UtilisateurControlleur;
import com.ouedraogo_issaka.cafe_management_system.dto.UtilisateurDto;
import com.ouedraogo_issaka.cafe_management_system.service.UtilisateurService;
import com.ouedraogo_issaka.cafe_management_system.utilaires.CafeUtilitaires;

@RestController
public class UtilisateurControlleurImpl implements UtilisateurControlleur {

    @Autowired
    UtilisateurService utilisateurService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return utilisateurService.signUp(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return utilisateurService.login(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UtilisateurDto>> findAllUtilisateur() {
        try {
            return utilisateurService.findAllUtilisateur();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return new ResponseEntity<List<UtilisateurDto>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUtilisateur(Map<String, String> requestMap) {
        try {
            return utilisateurService.updateUtilisateur(requestMap);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> verifierToken() {
        try {
            return utilisateurService.verifierToken();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changerPassword(Map<String, String> requestMap) {
        try {
            return utilisateurService.changerPassword(requestMap);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> passwordOublie(Map<String, String> requestMap) {
        try {
            return utilisateurService.passwordOublie(requestMap);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
