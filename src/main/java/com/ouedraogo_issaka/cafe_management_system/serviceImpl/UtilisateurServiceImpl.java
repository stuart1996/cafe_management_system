package com.ouedraogo_issaka.cafe_management_system.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.ouedraogo_issaka.cafe_management_system.JWT.CustomerUserDetailsService;
import com.ouedraogo_issaka.cafe_management_system.JWT.JwtFilter;
import com.ouedraogo_issaka.cafe_management_system.JWT.JwtUtilitaire;
import com.ouedraogo_issaka.cafe_management_system.constants.CafeConstant;
import com.ouedraogo_issaka.cafe_management_system.dto.UtilisateurDto;
import com.ouedraogo_issaka.cafe_management_system.modeles.Utilisateur;
import com.ouedraogo_issaka.cafe_management_system.repository.UtilisateurRepository;
import com.ouedraogo_issaka.cafe_management_system.service.UtilisateurService;
import com.ouedraogo_issaka.cafe_management_system.utilaires.CafeUtilitaires;
import com.ouedraogo_issaka.cafe_management_system.utilaires.EmailUtilitaires;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    JwtUtilitaire jwtUtilitaire;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtilitaires emailUtilitaires;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("À l'intérieur de l'inscription {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                Utilisateur user = utilisateurRepository.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    utilisateurRepository.save(getUserFromMap(requestMap));
                    return CafeUtilitaires.getResponseEntity("Enregistré avec succès.", HttpStatus.OK);
                }else{
                    return CafeUtilitaires.getResponseEntity("L'email existe déjà.", HttpStatus.BAD_REQUEST);
                }

            }else{
                return CafeUtilitaires.getResponseEntity(CafeConstant.DONNEES_INVALIDES, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception exception) {
            exception.fillInStackTrace();
            
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("nomComplet") && requestMap.containsKey("telephone")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private Utilisateur getUserFromMap(Map<String, String> requesMap) {
        Utilisateur user = new Utilisateur();

        user.setNomComplet(requesMap.get("nomComplet"));
        user.setTelephone(requesMap.get("telephone"));
        user.setEmail(requesMap.get("email"));
        user.setPassword(requesMap.get("password"));
        user.setStatus("false");
        user.setRole("user");

        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("À l'intérieur de la connexion {}", requestMap);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (authentication.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\" :\""
                            + jwtUtilitaire.generateAccessToken(customerUserDetailsService.getUserDetail().getEmail(),
                                    customerUserDetailsService.getUserDetail().getRole())
                            + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>(
                            "{\"Message\":\"Veuillez attendre l'approbation de l'administrateur" + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception exception) {
            log.error("{}", exception);
        }
        return new ResponseEntity<String>(
                "{\"Message\":\"Mauvaises qualifications" + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UtilisateurDto>> findAllUtilisateur() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(utilisateurRepository.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUtilisateur(Map<String, String> requestMap) {
        log.info("À l'intérieur de la mise a jour {}", requestMap);
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Utilisateur> optional = utilisateurRepository.findById(Integer.parseInt(requestMap.get("id")));

                if (!optional.isEmpty()) {
                    utilisateurRepository.updateStatus(requestMap.get("status"),
                            Integer.parseInt(requestMap.get("id")));

                    envoyeMailAllAdmin(requestMap.get("status"), optional.get().getEmail(),
                            utilisateurRepository.getAllAdmin());
                    return CafeUtilitaires.getResponseEntity("Le status de l'utilisateur est modifié avec succès",
                            HttpStatus.OK);
                } else {
                    return CafeUtilitaires.getResponseEntity("Id de l'utilisateur n'existe pas", HttpStatus.OK);
                }
            } else {
                return CafeUtilitaires.getResponseEntity(CafeConstant.ACCES_NON_AUTORISE, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            exception.fillInStackTrace();

        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private void envoyeMailAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());

        if (status != null && status.equalsIgnoreCase("true")) {
            emailUtilitaires.envoyeSimpleMessage(jwtFilter.getCurrentUser(), "Compte approuvé",
                    "USER:-" + user + "\n est apprové par \nADMIN: " + jwtFilter.getCurrentUser(), allAdmin);
        } else {
            emailUtilitaires.envoyeSimpleMessage(jwtFilter.getCurrentUser(), "Compte désactivé",
                    "USER:-" + user + "\n est désactivé par \nADMIN: " + jwtFilter.getCurrentUser(), allAdmin);
        }

    }

    @Override
    public ResponseEntity<String> verifierToken() {
        return CafeUtilitaires.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changerPassword(Map<String, String> requestMap) {
        try {
            Utilisateur utilisateurObj = utilisateurRepository.findByEmail(jwtFilter.getCurrentUser());

            if (!utilisateurObj.equals(null)) {
                if (utilisateurObj.getPassword().equals(requestMap.get("ancienPassword"))) {
                    utilisateurObj.setPassword(requestMap.get("nouveauPassword"));
                    utilisateurRepository.save(utilisateurObj);
                    return CafeUtilitaires.getResponseEntity("Mot de passe modifier avec succès", HttpStatus.OK);
                }
                return CafeUtilitaires.getResponseEntity("Ancien mot de passe incorrect", HttpStatus.BAD_REQUEST);
            }
            return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {
            exception.fillInStackTrace();

        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> passwordOublie(Map<String, String> requestMap) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByEmail(requestMap.get("email"));

            if (!Objects.isNull(utilisateur) && !Strings.isNullOrEmpty(utilisateur.getEmail())) {
                emailUtilitaires.passwordOublie(utilisateur.getEmail(),
                        "Informations d’identification par Cafe Management System.", utilisateur.getPassword());
                return CafeUtilitaires.getResponseEntity(
                        "Vérifiez votre courrier pour les informations d'identification", HttpStatus.OK);
            }
        } catch (Exception exception) {
            exception.fillInStackTrace();

        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
