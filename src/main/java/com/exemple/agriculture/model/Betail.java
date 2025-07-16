package com.exemple.agriculture.model;

import jakarta.persistence.*;

@Entity
public class Betail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;           // Nom de l’animal
    private String type;          // vache, mouton, chèvre, etc.
    private String race;
    private String dateNaissance; // JJ/MM/AAAA
    private double poids;         // en kg
    private String notes;

    // Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getRace() { return race; }

    public void setRace(String race) { this.race = race; }

    public String getDateNaissance() { return dateNaissance; }

    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    public double getPoids() { return poids; }

    public void setPoids(double poids) { this.poids = poids; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }
}
