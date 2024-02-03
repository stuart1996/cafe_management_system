package com.ouedraogo_issaka.cafe_management_system.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ouedraogo_issaka.cafe_management_system.JWT.JwtFilter;
import com.ouedraogo_issaka.cafe_management_system.constants.CafeConstant;
import com.ouedraogo_issaka.cafe_management_system.dto.ProduitDto;
import com.ouedraogo_issaka.cafe_management_system.modeles.Categorie;
import com.ouedraogo_issaka.cafe_management_system.modeles.Produit;
import com.ouedraogo_issaka.cafe_management_system.repository.ProduitRepository;
import com.ouedraogo_issaka.cafe_management_system.service.ProduitService;
import com.ouedraogo_issaka.cafe_management_system.utilaires.CafeUtilitaires;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> saveProduit(Map<String, String> requestMap) {
        log.info("À l'intérieur de la categorie {}", requestMap);
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProduitMap(requestMap, false)) {
                    produitRepository.save(getProduitFromMap(requestMap, false));
                    return CafeUtilitaires.getResponseEntity("Produit ajouté avec succès", HttpStatus.OK);
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

    private boolean validateProduitMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("nom")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Produit getProduitFromMap(Map<String, String> requesMap, Boolean isAjout) {

        Categorie categorie = new Categorie();
        categorie.setId(Integer.parseInt(requesMap.get("categorieId")));

        Produit produit = new Produit();

        if (isAjout) {
            produit.setId(Integer.parseInt(requesMap.get("id")));
        } else {
            produit.setStatus("true");
        }
        produit.setNom(requesMap.get("nom"));
        produit.setCategorie(categorie);
        produit.setDescription(requesMap.get("description"));
        produit.setPrix(Integer.parseInt(requesMap.get("prix")));

        return produit;
    }

    @Override
    public ResponseEntity<List<ProduitDto>> findAllProduit() {
        try {
            return new ResponseEntity<>(produitRepository.getAllProduit(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduit(Map<String, String> requestMap) {
        log.info("À l'intérieur de la categorie {}", requestMap);
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProduitMap(requestMap, true)) {
                    Optional<Produit> optional = produitRepository.findById(Integer.parseInt(requestMap.get("id")));

                    if (!optional.isEmpty()) {
                        Produit produit = getProduitFromMap(requestMap, true);
                        produit.setStatus(optional.get().getStatus());
                        produitRepository.save(produit);
                        return CafeUtilitaires.getResponseEntity("Produit modifié avec succès", HttpStatus.OK);
                    }
                    return CafeUtilitaires.getResponseEntity("Produit id n'existe pas", HttpStatus.OK);
                }
                return CafeUtilitaires.getResponseEntity(CafeConstant.DONNEES_INVALIDES, HttpStatus.BAD_REQUEST);

            }
            return CafeUtilitaires.getResponseEntity(CafeConstant.ACCES_NON_AUTORISE, HttpStatus.UNAUTHORIZED);

        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduit(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Produit> optional = produitRepository.findById(id);
                if (!optional.isEmpty()) {
                    produitRepository.deleteById(id);
                    return CafeUtilitaires.getResponseEntity("Produit supprimé avec succès", HttpStatus.OK);
                }
                return CafeUtilitaires.getResponseEntity("Produit id n'existe pas", HttpStatus.OK);

            }
            return CafeUtilitaires.getResponseEntity(CafeConstant.ACCES_NON_AUTORISE, HttpStatus.UNAUTHORIZED);

        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatutProduit(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Produit> optional = produitRepository.findById(Integer.parseInt(requestMap.get("id")));

                if (!optional.isEmpty()) {
                    produitRepository.modifierStatutProduit(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return CafeUtilitaires.getResponseEntity("Le statut du produit modifié avec succès", HttpStatus.OK);
                }
                return CafeUtilitaires.getResponseEntity("Produit id n'existe pas", HttpStatus.OK);

            }
            return CafeUtilitaires.getResponseEntity(CafeConstant.ACCES_NON_AUTORISE, HttpStatus.UNAUTHORIZED);

        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProduitDto>> findProduitByCategorie(Integer id) {
        try {
            return new ResponseEntity<>(produitRepository.getProduitByCategorie(id), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProduitDto>> findProduitById(Integer id) {
        try {
            return new ResponseEntity<>(produitRepository.getProduitById(id), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
