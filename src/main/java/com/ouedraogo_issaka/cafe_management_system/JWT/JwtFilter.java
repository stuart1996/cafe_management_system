package com.ouedraogo_issaka.cafe_management_system.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtilitaire jwtUtilitaire;

    @Autowired
    private CustomerUserDetailsService service;

    Claims claims = null;
    private String username = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        // if (request.getServletPath().matches("/utilisateur/login|/utilisateur/signup|/utilisateur/motPasseOublie|/swagger-ui")) {
        //     filterChain.doFilter(request, response);
        // } else {

            final String authHeader = request.getHeader("Authorization");
            String token = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtUtilitaire.extractUsername(token);
                claims = jwtUtilitaire.extractAllClaims(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = service.loadUserByUsername(username);

                if (jwtUtilitaire.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        //}
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser() {
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser() {
        return username;
    }
}
