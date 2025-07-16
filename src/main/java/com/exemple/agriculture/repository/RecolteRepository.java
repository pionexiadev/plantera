package com.exemple.agriculture.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exemple.agriculture.model.Recolte;

public interface RecolteRepository extends JpaRepository<Recolte, Long> {
    List<Recolte> findByParcelleUtilisateurEmail(String email);
}
