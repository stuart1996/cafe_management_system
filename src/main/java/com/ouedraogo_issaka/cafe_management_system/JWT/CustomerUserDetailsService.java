package com.ouedraogo_issaka.cafe_management_system.JWT;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ouedraogo_issaka.cafe_management_system.modeles.Utilisateur;
import com.ouedraogo_issaka.cafe_management_system.repository.UtilisateurRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UtilisateurRepository utilisateurRepository;
    Utilisateur userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("À l'intérieur de l'inscription {}", username);
        userDetail = utilisateurRepository.findByEmailId(username);

        if (!Objects.isNull(userDetail)) {
            return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé.");
        }
    }

    public Utilisateur getUserDetail() {
        return userDetail;
    }
}
