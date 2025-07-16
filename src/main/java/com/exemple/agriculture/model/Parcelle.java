package com.exemple.agriculture.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parcelle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private double surface;
    private String localisation;
    private String typeSol;
    private String systemeIrrigation;

    @Column(nullable = false)
    private boolean disponible = true;

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @OneToMany(mappedBy = "parcelle", cascade = CascadeType.ALL)
    private List<Culture> cultures = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "utilisateur_id") 
    private Utilisateur utilisateur;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Lob
    private String notes;

    // 🟩 Getters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public double getSurface() {
        return surface;
    }

    public String getTypeSol() {
        return typeSol;
    }

    public String getSystemeIrrigation() {
        return systemeIrrigation;
    }

    public String getNotes() {
        return notes;
    }

    // 🟦 Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public void setTypeSol(String typeSol) {
        this.typeSol = typeSol;
    }

    public void setSystemeIrrigation(String systemeIrrigation) {
        this.systemeIrrigation = systemeIrrigation;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
