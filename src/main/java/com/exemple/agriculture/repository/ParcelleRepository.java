package com.exemple.agriculture.repository;

import com.exemple.agriculture.model.Parcelle;
import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.agriculture.model.Utilisateur;
import java.util.List;

public interface ParcelleRepository extends JpaRepository<Parcelle, Long> {
    List<Parcelle> findByUtilisateur(Utilisateur utilisateur);
}
