package com.ouedraogo_issaka.cafe_management_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProduitDto {

    private Integer id;
    private String nom;
    private String description;
    private Integer prix;
    private String status;
    private Integer idCategorie;
    private String nomCategorie;

    public ProduitDto(Integer id, String nom, String description, Integer prix, String status, Integer idCategorie,
            String nomCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.idCategorie = idCategorie;
        this.nomCategorie = nomCategorie;
        this.status = status;
    }

    public ProduitDto(Integer id, String nom){
        this.id = id;
        this.nom = nom;
    }

    public ProduitDto(Integer id, String nom, String description, Integer prix){
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
    }
}
