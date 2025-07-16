package com.exemple.agriculture.model;

import jakarta.persistence.*;

@Entity
public class Recolte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parcelle_id")
    private Parcelle parcelle;
    
    @ManyToOne
    @JoinColumn(name = "culture_id")
    private Culture culture;


    private String dateRecolte;
    private double quantite; // en kg
    private String qualite;
    private String statut; // Planifiée, Réalisée, Annulée…
    private double couts;
    private int heuresTravail;
    private String conditionsMeteo;
    private String notes;

    // Getters & Setters

    public Culture getCulture() {
        return culture;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Parcelle getParcelle() { return parcelle; }

    public void setParcelle(Parcelle parcelle) { this.parcelle = parcelle; }

    public String getDateRecolte() { return dateRecolte; }

    public void setDateRecolte(String dateRecolte) { this.dateRecolte = dateRecolte; }

    public double getQuantite() { return quantite; }

    public void setQuantite(double quantite) { this.quantite = quantite; }

    public String getQualite() { return qualite; }

    public void setQualite(String qualite) { this.qualite = qualite; }

    public String getStatut() { return statut; }

    public void setStatut(String statut) { this.statut = statut; }

    public double getCouts() { return couts; }

    public void setCouts(double couts) { this.couts = couts; }

    public int getHeuresTravail() { return heuresTravail; }

    public void setHeuresTravail(int heuresTravail) { this.heuresTravail = heuresTravail; }

    public String getConditionsMeteo() { return conditionsMeteo; }

    public void setConditionsMeteo(String conditionsMeteo) { this.conditionsMeteo = conditionsMeteo; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }
}
