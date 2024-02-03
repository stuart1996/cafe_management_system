package com.ouedraogo_issaka.cafe_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.ouedraogo_issaka.cafe_management_system.dto.UtilisateurDto;
import com.ouedraogo_issaka.cafe_management_system.modeles.Utilisateur;

import jakarta.transaction.Transactional;

public interface UtilisateurRepository extends JpaRepository <Utilisateur, Integer>{
    
    Utilisateur findByEmailId(@Param("email") String email);
    
    List<UtilisateurDto> getAllUser();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    List<String>  getAllAdmin();

    Utilisateur findByEmail(String email);
}
