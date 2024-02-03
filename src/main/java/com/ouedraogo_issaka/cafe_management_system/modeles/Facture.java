package com.ouedraogo_issaka.cafe_management_system.modeles;

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

@NamedQuery(name = "Facture.getAllFactures", query = "select f from Facture f order by f.id desc")
@NamedQuery(name = "Facture.getFactureByUsername", query = "select f from Facture f where f.creePar =:username order by f.id desc")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "facture")
public class Facture {

    public Facture(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "modepayement")
    private String modePayement;

    @Column(name = "total")
    private Integer total;

    @Column(name = "detailproduit", columnDefinition = "json")
    private String detailProduit;

    @Column(name = "creePar")
    private String creePar;
}
