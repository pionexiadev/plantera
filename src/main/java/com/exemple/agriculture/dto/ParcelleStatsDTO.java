package com.exemple.agriculture.dto;

public class ParcelleStatsDTO {
    private int totalParcelles;
    private double surfaceTotale;
    private double hectaresDisponibles;

    // Constructeur
    public ParcelleStatsDTO(int totalParcelles, double surfaceTotale, double hectaresDisponibles) {
        this.totalParcelles = totalParcelles;
        this.surfaceTotale = surfaceTotale;
        this.hectaresDisponibles = hectaresDisponibles;
    }

    // Getters
    public int getTotalParcelles() {
        return totalParcelles;
    }

    public double getSurfaceTotale() {
        return surfaceTotale;
    }

    public double getHectaresDisponibles() {
        return hectaresDisponibles;
    }
}
