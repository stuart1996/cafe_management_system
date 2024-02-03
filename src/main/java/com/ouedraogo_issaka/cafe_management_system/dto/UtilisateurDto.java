package com.ouedraogo_issaka.cafe_management_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UtilisateurDto {

    public UtilisateurDto(Integer id, String nomComplet, String telephone, String email,
            String status) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.telephone = telephone;
        this.email = email;
        this.status = status;
    }

    private Integer id;

    private String nomComplet;

    private String telephone;

    private String email;

    private String status;
}