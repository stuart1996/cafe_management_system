package com.ouedraogo_issaka.cafe_management_system.controlleurImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ouedraogo_issaka.cafe_management_system.JWT.JwtFilter;
import com.ouedraogo_issaka.cafe_management_system.constants.CafeConstant;
import com.ouedraogo_issaka.cafe_management_system.controlleur.FactureControleur;
import com.ouedraogo_issaka.cafe_management_system.modeles.Facture;
import com.ouedraogo_issaka.cafe_management_system.repository.FactureRepository;
import com.ouedraogo_issaka.cafe_management_system.service.FactureService;
import com.ouedraogo_issaka.cafe_management_system.utilaires.CafeUtilitaires;

@RestController
public class FactureControleurImpl implements FactureControleur{

    @Autowired
    FactureService factureService;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    FactureRepository factureRepository;

    @Override
    public ResponseEntity<String> generedRapport(Map<String, Object> requestMap) {
        try {
            return factureService.generedRapport(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Facture>> findAllFacture() {
        List<Facture> factureList = new ArrayList<>();

        if (jwtFilter.isAdmin()) {
            factureList = factureRepository.getAllFactures();
        }else{
            factureList = factureRepository.getFactureByUsername(jwtFilter.getCurrentUser());
        }
        return new ResponseEntity<>(factureList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> addPdf(Map<String, Object> requestMap) {
        try {
            return factureService.addPdf(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteFacture(Integer id) {
        try {
            return factureService.deleteFacture(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
     
}
