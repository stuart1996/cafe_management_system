package com.ouedraogo_issaka.cafe_management_system.modeles;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

@NamedQuery(name = "Utilisateur.findByEmailId", query = "select u from Utilisateur u where u.email=:email")
@NamedQuery(name = "Utilisateur.getAllUser", query = "select new com.ouedraogo_issaka.cafe_management_system.dto.UtilisateurDto(u.id, u.nomComplet, u.telephone, u.email, u.status) from Utilisateur u where u.role = 'user'")
@NamedQuery(name = "Utilisateur.getAllAdmin", query = "select u.email from Utilisateur u where u.role ='admin'")
@NamedQuery(name = "Utilisateur.updateStatus", query = "update Utilisateur u set u.status=:status where u.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

    public Utilisateur() {
    }

    public Utilisateur(String nomComplet, String telephone, String email, String password, String status, String role) {
        this.nomComplet = nomComplet;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nomComplet")
    private String nomComplet;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}
