package com.ouedraogo_issaka.cafe_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.ouedraogo_issaka.cafe_management_system.dto.ProduitDto;
import com.ouedraogo_issaka.cafe_management_system.modeles.Produit;

import jakarta.transaction.Transactional;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    List<ProduitDto> getAllProduit();

    @Modifying
    @Transactional
    Integer modifierStatutProduit(@Param("status") String status, @Param("id") Integer id);

    List<ProduitDto> getProduitByCategorie(@Param("id") Integer id);

    List<ProduitDto> getProduitById(@Param("id") Integer id);

}
