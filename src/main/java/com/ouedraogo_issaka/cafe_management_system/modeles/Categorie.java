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

@NamedQuery(name = "Categorie.getAllCategorie", query = "select c from Categorie c where c.id in (select p.id from Produit p where p.status='true')")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "categorie")
public class Categorie implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    public Categorie() {}

    public Categorie(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }
}
