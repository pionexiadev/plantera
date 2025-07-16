package com.exemple.agriculture.dto;

import com.exemple.agriculture.model.ProfilUtilisateur;

public class RegisterRequest {

    private String nom;
    private String email;
    private String motDePasse;
    private ProfilUtilisateur profil;
    private String nomEntreprise;

    // Getters et setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public ProfilUtilisateur getProfil() { return profil; }
    public void setProfil(ProfilUtilisateur profil) { this.profil = profil; }

    public String getNomEntreprise() { return nomEntreprise; }
    public void setNomEntreprise(String nomEntreprise) { this.nomEntreprise = nomEntreprise; }
}
