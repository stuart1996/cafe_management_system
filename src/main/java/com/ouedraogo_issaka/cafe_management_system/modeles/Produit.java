package com.ouedraogo_issaka.cafe_management_system.modeles;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

@NamedQuery(name = "Produit.getAllProduit", query = "select new com.ouedraogo_issaka.cafe_management_system.dto.ProduitDto(p.id, p.nom, p.description, p.prix, p.status, p.categorie.id, p.categorie.nom) from Produit p")
@NamedQuery(name = "Produit.modifierStatutProduit", query = "update Produit p set p.status=:status where p.id=:id")
@NamedQuery(name = "Produit.getProduitByCategorie", query = "select new com.ouedraogo_issaka.cafe_management_system.dto.ProduitDto(p.id, p.nom) from Produit p where p.categorie.id=:id and p.status='true'")
@NamedQuery(name = "Produit.getProduitById", query = "select new com.ouedraogo_issaka.cafe_management_system.dto.ProduitDto(p.id, p.nom, p.description,p.prix) from Produit p where p.id=:id")



@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "produit")
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_fk", nullable = false)
    private Categorie categorie;

    @Column(name = "description")
    private String description;

    @Column(name = "prix")
    private Integer prix;

    @Column(name = "status")
    private String status;
}
