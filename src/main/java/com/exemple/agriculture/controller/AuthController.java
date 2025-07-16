package com.exemple.agriculture.controller;

import com.exemple.agriculture.dto.AuthResponse;
import com.exemple.agriculture.dto.LoginRequest;
import com.exemple.agriculture.dto.RegisterRequest;
import com.exemple.agriculture.model.Role;
import com.exemple.agriculture.model.Utilisateur;
import com.exemple.agriculture.repository.UtilisateurRepository;
import com.exemple.agriculture.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // ✅ INSCRIPTION
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Utilisateur user = new Utilisateur();
        user.setNom(request.getNom());
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        user.setProfil(request.getProfil());
        user.setRole(Role.ROLE_USER);
        user.setNomEntreprise(request.getNomEntreprise());

        utilisateurRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token); // Seulement le token
    }


    // ✅ CONNEXION
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        Utilisateur user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    // ✅ INFOS DU USER CONNECTÉ
    @GetMapping("/me")
    public Utilisateur getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant ou invalide");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            throw new RuntimeException("Token invalide");
        }

        String email = jwtService.getEmailFromToken(token);
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
