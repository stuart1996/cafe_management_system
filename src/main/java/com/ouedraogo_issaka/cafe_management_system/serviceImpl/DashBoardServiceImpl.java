package com.ouedraogo_issaka.cafe_management_system.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ouedraogo_issaka.cafe_management_system.repository.CategorieRepository;
import com.ouedraogo_issaka.cafe_management_system.repository.FactureRepository;
import com.ouedraogo_issaka.cafe_management_system.repository.ProduitRepository;
import com.ouedraogo_issaka.cafe_management_system.service.DashBoardService;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    FactureRepository factureRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("categorie", categorieRepository.count());
        map.put("produit", produitRepository.count());
        map.put("facture", factureRepository.count());

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

}
