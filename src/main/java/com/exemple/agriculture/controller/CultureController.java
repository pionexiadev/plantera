package com.exemple.agriculture.controller;

import com.exemple.agriculture.model.Culture;
import com.exemple.agriculture.model.Parcelle;
import com.exemple.agriculture.model.Utilisateur;
import com.exemple.agriculture.repository.CultureRepository;
import com.exemple.agriculture.repository.ParcelleRepository;
import com.exemple.agriculture.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cultures")

public class CultureController {

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private ParcelleRepository parcelleRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // 🔍 Toutes les cultures de l’utilisateur connecté
    @GetMapping
    public List<Culture> getCulturesByUser(@AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return cultureRepository.findByParcelle_Utilisateur(utilisateur);
    }


    // 🔍 Culture par ID
    @GetMapping("/{id}")
    public Culture getCultureById(@PathVariable Long id) {
        return cultureRepository.findById(id).orElse(null);
    }

    // ➕ Ajouter une culture (vérifie si la parcelle appartient à l’utilisateur connecté)
    @PostMapping
public Culture createCulture(@RequestBody Culture culture, @AuthenticationPrincipal UserDetails userDetails) {
    Utilisateur utilisateur = utilisateurRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    Long parcelleId = culture.getParcelle().getId();
    Optional<Parcelle> parcelleOptional = parcelleRepository.findById(parcelleId);

    if (parcelleOptional.isEmpty()) {
        throw new RuntimeException("❌ Parcelle introuvable avec l'ID : " + parcelleId);
    }

    Parcelle parcelle = parcelleOptional.get();

    if (!parcelle.getUtilisateur().getId().equals(utilisateur.getId())) {
        throw new RuntimeException("🚫 Vous n’avez pas accès à cette parcelle.");
    }

    culture.setParcelle(parcelle);
    return cultureRepository.save(culture);
}


    // ✏️ Modifier une culture
    @PutMapping("/{id}")
public Culture updateCulture(@PathVariable Long id, @RequestBody Culture updatedCulture,
                              @AuthenticationPrincipal UserDetails userDetails) {
    Utilisateur utilisateur = utilisateurRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    Culture culture = cultureRepository.findById(id).orElse(null);
    if (culture == null) {
        throw new RuntimeException("❌ Culture introuvable");
    }

    if (!culture.getParcelle().getUtilisateur().getId().equals(utilisateur.getId())) {
        throw new RuntimeException("🚫 Vous ne pouvez pas modifier cette culture.");
    }

    culture.setNom(updatedCulture.getNom());
    culture.setSurface(updatedCulture.getSurface());
    culture.setDatePlantation(updatedCulture.getDatePlantation());
    culture.setDateRecolteEstimee(updatedCulture.getDateRecolteEstimee());
    culture.setRendementAttendu(updatedCulture.getRendementAttendu());
    culture.setNotes(updatedCulture.getNotes());

    return cultureRepository.save(culture);
}


    // ❌ Supprimer une culture (si l’utilisateur en est le propriétaire)
    @DeleteMapping("/{id}")
public void deleteCulture(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
    Utilisateur utilisateur = utilisateurRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    Optional<Culture> cultureOptional = cultureRepository.findById(id);

    if (cultureOptional.isPresent()) {
        Culture culture = cultureOptional.get();
        if (!culture.getParcelle().getUtilisateur().getId().equals(utilisateur.getId())) {
            throw new RuntimeException("🚫 Vous ne pouvez pas supprimer cette culture.");
        }

        cultureRepository.deleteById(id);
    }
}

}
