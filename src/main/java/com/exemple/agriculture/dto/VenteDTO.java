package com.exemple.agriculture.dto;

public class VenteDTO {
    private Long id;
    private String client;
    private double quantite;
    private double prixUnitaire;
    private double total;
    private String dateVente;
    private String statut;

    private String cultureNom;
    private String parcelleNom;

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

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getCultureNom() { return cultureNom; }
    public void setCultureNom(String cultureNom) { this.cultureNom = cultureNom; }

    public String getParcelleNom() { return parcelleNom; }
    public void setParcelleNom(String parcelleNom) { this.parcelleNom = parcelleNom; }
}
