package com.exemple.agriculture.controller;

import com.exemple.agriculture.model.Recolte;
import com.exemple.agriculture.model.Parcelle;
import com.exemple.agriculture.repository.RecolteRepository;
import com.exemple.agriculture.repository.ParcelleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recoltes")
public class RecolteController {

    @Autowired
    private RecolteRepository recolteRepository;

    @Autowired
    private ParcelleRepository parcelleRepository;

    // ✅ Lire les récoltes de l'utilisateur connecté
    @GetMapping
    public List<Recolte> getRecoltesByUtilisateur(Authentication authentication) {
        String email = authentication.getName();
        return recolteRepository.findByParcelleUtilisateurEmail(email);
    }

    // ✅ Lire une récolte par ID si elle appartient à l'utilisateur
    @GetMapping("/{id}")
    public Recolte getRecolteById(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        Recolte recolte = recolteRepository.findById(id).orElse(null);

        if (recolte != null && recolte.getParcelle().getUtilisateur().getEmail().equals(email)) {
            return recolte;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit à cette récolte");
        }
    }

    // ✅ Créer une récolte uniquement sur une parcelle de l'utilisateur connecté
    @PostMapping
public Recolte createRecolte(@RequestBody Recolte recolte, Authentication authentication) {
    String email = authentication.getName();

    if (recolte.getParcelle() == null || recolte.getParcelle().getId() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parcelle manquante");
    }

    Long parcelleId = recolte.getParcelle().getId();
    Optional<Parcelle> parcelleOptional = parcelleRepository.findById(parcelleId);

    if (parcelleOptional.isPresent() && parcelleOptional.get().getUtilisateur().getEmail().equals(email)) {
        recolte.setParcelle(parcelleOptional.get());
        return recolteRepository.save(recolte);
    } else {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'avez pas accès à cette parcelle");
    }
}


    // ✅ Modifier une récolte uniquement si elle appartient à l'utilisateur
    @PutMapping("/{id}")
    public Recolte updateRecolte(@PathVariable Long id, @RequestBody Recolte updatedRecolte, Authentication authentication) {
        String email = authentication.getName();
        Recolte recolte = recolteRepository.findById(id).orElse(null);

        if (recolte == null || !recolte.getParcelle().getUtilisateur().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit à cette récolte");
        }

        // Mise à jour
        recolte.setDateRecolte(updatedRecolte.getDateRecolte());
        recolte.setQuantite(updatedRecolte.getQuantite());
        recolte.setQualite(updatedRecolte.getQualite());
        recolte.setStatut(updatedRecolte.getStatut());
        recolte.setCouts(updatedRecolte.getCouts());
        recolte.setHeuresTravail(updatedRecolte.getHeuresTravail());
        recolte.setConditionsMeteo(updatedRecolte.getConditionsMeteo());
        recolte.setNotes(updatedRecolte.getNotes());

        // Mise à jour de la parcelle si elle appartient bien à l'utilisateur
        if (updatedRecolte.getParcelle() != null) {
            Long parcelleId = updatedRecolte.getParcelle().getId();
            parcelleRepository.findById(parcelleId).ifPresent(parcelle -> {
                if (parcelle.getUtilisateur().getEmail().equals(email)) {
                    recolte.setParcelle(parcelle);
                }
            });
        }

        return recolteRepository.save(recolte);
    }

    // ✅ Supprimer une récolte uniquement si elle appartient à l'utilisateur
    @DeleteMapping("/{id}")
    public void deleteRecolte(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        Recolte recolte = recolteRepository.findById(id).orElse(null);

        if (recolte != null && recolte.getParcelle().getUtilisateur().getEmail().equals(email)) {
            recolteRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit à cette récolte");
        }
    }
}
