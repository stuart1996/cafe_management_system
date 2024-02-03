package com.ouedraogo_issaka.cafe_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ouedraogo_issaka.cafe_management_system.modeles.Categorie;

public interface CategorieRepository extends JpaRepository <Categorie, Integer> {
    
    List<Categorie>  getAllCategorie();
}
