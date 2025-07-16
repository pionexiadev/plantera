package com.exemple.agriculture.repository;

import com.exemple.agriculture.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepository extends JpaRepository<Vente, Long> {
}
