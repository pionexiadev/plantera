package com.exemple.agriculture.model;

import jakarta.persistence.*;

@Entity
public class Culture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;                        // Ex: Tomates, Blé
    private double surface;                    // En hectares
    private String datePlantation;             // Format JJ/MM/AAAA
    private String dateRecolteEstimee;         // Format JJ/MM/AAAA
    private double rendementAttendu;           // En kg
    private String notes;

    @ManyToOne
    @JoinColumn(name = "parcelle_id")
    private Parcelle parcelle;

    // Getters & Setters

    
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public double getSurface() { return surface; }

    public void setSurface(double surface) { this.surface = surface; }

    public String getDatePlantation() { return datePlantation; }

    public void setDatePlantation(String datePlantation) { this.datePlantation = datePlantation; }

    public String getDateRecolteEstimee() { return dateRecolteEstimee; }

    public void setDateRecolteEstimee(String dateRecolteEstimee) { this.dateRecolteEstimee = dateRecolteEstimee; }

    public double getRendementAttendu() { return rendementAttendu; }

    public void setRendementAttendu(double rendementAttendu) { this.rendementAttendu = rendementAttendu; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public Parcelle getParcelle() { return parcelle; }

    public void setParcelle(Parcelle parcelle) { this.parcelle = parcelle; }
}
