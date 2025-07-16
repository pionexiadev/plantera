package com.exemple.agriculture.model;

import jakarta.persistence.*;

@Entity
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String client;
    private double quantite;      // en kg
    private double prixUnitaire;  // €/kg
    private double total;

    private String dateVente;
    private String dateLivraison;
    private String datePaiement;

    private String statut; // En attente, Payée, Annulée…
    private String notes;

    @ManyToOne
    @JoinColumn(name = "recolte_id")
    private Recolte recolte;

    // Getters & Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getClient() { return client; }

    public void setClient(String client) { this.client = client; }

    public double getQuantite() { return quantite; }

    public void setQuantite(double quantite) { this.quantite = quantite; }

    public double getPrixUnitaire() { return prixUnitaire; }

    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public double getTotal() { return total; }

    public void setTotal(double total) { this.total = total; }

    public String getDateVente() { return dateVente; }

    public void setDateVente(String dateVente) { this.dateVente = dateVente; }

    public String getDateLivraison() { return dateLivraison; }

    public void setDateLivraison(String dateLivraison) { this.dateLivraison = dateLivraison; }

    public String getDatePaiement() { return datePaiement; }

    public void setDatePaiement(String datePaiement) { this.datePaiement = datePaiement; }

    public String getStatut() { return statut; }

    public void setStatut(String statut) { this.statut = statut; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public Recolte getRecolte() { return recolte; }

    public void setRecolte(Recolte recolte) { this.recolte = recolte; }
}
