package com.exemple.agriculture.repository;

import com.exemple.agriculture.model.Culture;
import com.exemple.agriculture.model.Utilisateur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CultureRepository extends JpaRepository<Culture, Long> {
    List<Culture> findByParcelle_Utilisateur(Utilisateur utilisateur);

}
