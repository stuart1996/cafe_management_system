package com.ouedraogo_issaka.cafe_management_system.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.ouedraogo_issaka.cafe_management_system.JWT.JwtFilter;
import com.ouedraogo_issaka.cafe_management_system.constants.CafeConstant;
import com.ouedraogo_issaka.cafe_management_system.modeles.Categorie;
import com.ouedraogo_issaka.cafe_management_system.repository.CategorieRepository;
import com.ouedraogo_issaka.cafe_management_system.service.CategorieService;
import com.ouedraogo_issaka.cafe_management_system.utilaires.CafeUtilitaires;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> saveCategorie(Map<String, String> requestMap) {
        log.info("À l'intérieur de la categorie {}", requestMap);
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategorieMap(requestMap, false)) {
                    categorieRepository.save(getCategorieFromMap(requestMap, false));
                    return CafeUtilitaires.getResponseEntity("Categorie ajouté avec succès", HttpStatus.OK);
                }
                return CafeUtilitaires.getResponseEntity(CafeConstant.DONNEES_INVALIDES, HttpStatus.BAD_REQUEST);

            } else {
                return CafeUtilitaires.getResponseEntity(CafeConstant.ACCES_NON_AUTORISE, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategorieMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("nom")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Categorie getCategorieFromMap(Map<String, String> requesMap, Boolean isAjout) {
        Categorie categorie = new Categorie();

        if (isAjout) {
            categorie.setId(Integer.parseInt(requesMap.get("id")));
        }
        categorie.setNom(requesMap.get("nom"));

        return categorie;
    }

    @Override
    public ResponseEntity<List<Categorie>> findAllCategorie(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("\nà l'intérieur if\n");
                return new ResponseEntity<List<Categorie>>(categorieRepository.getAllCategorie(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categorieRepository.findAll(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategorie(Map<String, String> requestMap) {
        log.info("À l'intérieur de la categorie {}", requestMap);
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategorieMap(requestMap, true)) {
                    Optional<Categorie> optional = categorieRepository.findById(Integer.parseInt(requestMap.get("id")));

                    if (!optional.isEmpty()) {
                        categorieRepository.save(getCategorieFromMap(requestMap, true));
                        return CafeUtilitaires.getResponseEntity("Categorie modifier avec succès", HttpStatus.OK);
                    } else {
                        return CafeUtilitaires.getResponseEntity("Categorie id n'existe pas", HttpStatus.OK);
                    }
                }
                return CafeUtilitaires.getResponseEntity(CafeConstant.DONNEES_INVALIDES, HttpStatus.BAD_REQUEST);

            } else {
                return CafeUtilitaires.getResponseEntity(CafeConstant.ACCES_NON_AUTORISE, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
