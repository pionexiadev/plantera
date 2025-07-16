package com.exemple.agriculture.controller;

import com.exemple.agriculture.dto.ParcelleStatsDTO;
import com.exemple.agriculture.model.Parcelle;
import com.exemple.agriculture.model.Utilisateur;
import com.exemple.agriculture.repository.ParcelleRepository;
import com.exemple.agriculture.repository.UtilisateurRepository;
import com.exemple.agriculture.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parcelles")
public class ParcelleController {

    @Autowired
    private ParcelleRepository parcelleRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtService jwtService;

    // 🔐 Récupération de l'utilisateur connecté depuis le token
    private Utilisateur getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant ou invalide");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            throw new RuntimeException("Token invalide");
        }

        String email = jwtService.getEmailFromToken(token);
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    // ✅ 1. GET : Parcelles de l'utilisateur connecté
    @GetMapping
    public List<Parcelle> getAllParcelles(HttpServletRequest request) {
        Utilisateur currentUser = getCurrentUser(request);
        return parcelleRepository.findByUtilisateur(currentUser);
    }

    // ✅ 2. GET : une parcelle par ID (seulement si elle appartient à l'utilisateur)
    @GetMapping("/{id}")
    public ResponseEntity<Parcelle> getParcelleById(@PathVariable Long id, HttpServletRequest request) {
        Utilisateur currentUser = getCurrentUser(request);
        Optional<Parcelle> parcelle = parcelleRepository.findById(id);

        if (parcelle.isPresent() && parcelle.get().getUtilisateur().getId().equals(currentUser.getId())) {
            return ResponseEntity.ok(parcelle.get());
        } else {
            return ResponseEntity.status(403).build(); // Interdit
        }
    }

    // ✅ 3. POST : ajouter une nouvelle parcelle liée à l'utilisateur connecté
    @PostMapping
    public Parcelle createParcelle(@RequestBody Parcelle parcelle, HttpServletRequest request) {
        Utilisateur currentUser = getCurrentUser(request);
        parcelle.setUtilisateur(currentUser);
        return parcelleRepository.save(parcelle);
    }

    // ✅ 4. PUT : modifier une parcelle (seulement si elle appartient à l'utilisateur)
    @PutMapping("/{id}")
    public ResponseEntity<Parcelle> updateParcelle(@PathVariable Long id,
                                                   @RequestBody Parcelle parcelleDetails,
                                                   HttpServletRequest request) {
        Utilisateur currentUser = getCurrentUser(request);
        Optional<Parcelle> optionalParcelle = parcelleRepository.findById(id);

        if (optionalParcelle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Parcelle parcelle = optionalParcelle.get();

        if (!parcelle.getUtilisateur().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build(); // Interdit
        }

        parcelle.setNom(parcelleDetails.getNom());
        parcelle.setLocalisation(parcelleDetails.getLocalisation());
        parcelle.setSurface(parcelleDetails.getSurface());
        parcelle.setTypeSol(parcelleDetails.getTypeSol());
        parcelle.setSystemeIrrigation(parcelleDetails.getSystemeIrrigation());
        parcelle.setNotes(parcelleDetails.getNotes());

        return ResponseEntity.ok(parcelleRepository.save(parcelle));
    }

    // ✅ 5. DELETE : supprimer une parcelle (seulement si elle appartient à l'utilisateur)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcelle(@PathVariable Long id, HttpServletRequest request) {
        Utilisateur currentUser = getCurrentUser(request);
        Optional<Parcelle> optionalParcelle = parcelleRepository.findById(id);

        if (optionalParcelle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Parcelle parcelle = optionalParcelle.get();

        if (!parcelle.getUtilisateur().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build(); // Interdit
        }

        parcelleRepository.delete(parcelle);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/stats")
    public ParcelleStatsDTO getStats(HttpServletRequest request) {
        Utilisateur currentUser = getCurrentUser(request);
        List<Parcelle> parcelles = parcelleRepository.findByUtilisateur(currentUser);

        int totalParcelles = parcelles.size();
        double surfaceTotale = parcelles.stream().mapToDouble(Parcelle::getSurface).sum();
        double hectaresDisponibles = parcelles.stream()
                                              .filter(Parcelle::isDisponible)
                                              .mapToDouble(Parcelle::getSurface)
                                              .sum();

        return new ParcelleStatsDTO(totalParcelles, surfaceTotale, hectaresDisponibles);
    }

}
