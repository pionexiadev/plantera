package com.exemple.agriculture.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Utilisateur implements Serializable {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nom;

	    @Column(unique = true)
	    private String email;

	    private String motDePasse;

	    @Enumerated(EnumType.STRING)
	    private Role role; // ROLE_USER ou ROLE_ADMIN

	    @Enumerated(EnumType.STRING)
	    private ProfilUtilisateur profil; // AGRICULTEUR, VETERINAIRE, etc.

	    private String nomEntreprise;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public ProfilUtilisateur getProfil() { return profil; }
    public void setProfil(ProfilUtilisateur profil) { this.profil = profil; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getNomEntreprise() { return nomEntreprise; }
    public void setNomEntreprise(String nomEntreprise) { this.nomEntreprise = nomEntreprise; }
    
   
}
