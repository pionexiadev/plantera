package com.exemple.agriculture.controller;

import com.exemple.agriculture.dto.VenteDTO;
import com.exemple.agriculture.model.Recolte;
import com.exemple.agriculture.model.Vente;
import com.exemple.agriculture.repository.RecolteRepository;
import com.exemple.agriculture.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventes")
@CrossOrigin(origins = "*")
public class VenteController {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private RecolteRepository recolteRepository;

    @GetMapping("/dto")
    public List<VenteDTO> getVentesAvecDetails() {
        List<Vente> ventes = venteRepository.findAll();
        List<VenteDTO> result = new ArrayList<>();

        for (Vente vente : ventes) {
            VenteDTO dto = new VenteDTO();
            dto.setId(vente.getId());
            dto.setClient(vente.getClient());
            dto.setQuantite(vente.getQuantite());
            dto.setPrixUnitaire(vente.getPrixUnitaire());
            dto.setTotal(vente.getTotal());
            dto.setDateVente(vente.getDateVente());
            dto.setStatut(vente.getStatut());

            if (vente.getRecolte() != null) {
                dto.setCultureNom(
                    vente.getRecolte().getCulture() != null ?
                    vente.getRecolte().getCulture().getNom() : "Culture inconnue"
                );

                dto.setParcelleNom(
                    (vente.getRecolte().getCulture() != null && vente.getRecolte().getCulture().getParcelle() != null) ?
                    vente.getRecolte().getCulture().getParcelle().getNom() : "Parcelle inconnue"
                );
            }

            result.add(dto);
        }

        return result;
    }


    @GetMapping("/{id}")
    public Vente getVenteById(@PathVariable Long id) {
        return venteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Vente createVente(@RequestBody Vente vente) {
        if (vente.getRecolte() != null) {
            Optional<Recolte> recolteOpt = recolteRepository.findById(vente.getRecolte().getId());
            recolteOpt.ifPresent(vente::setRecolte);
        }
        vente.setTotal(vente.getQuantite() * vente.getPrixUnitaire());
        return venteRepository.save(vente);
    }

    @PutMapping("/{id}")
    public Vente updateVente(@PathVariable Long id, @RequestBody Vente venteDetails) {
        Vente vente = venteRepository.findById(id).orElse(null);
        if (vente != null) {
            vente.setClient(venteDetails.getClient());
            vente.setQuantite(venteDetails.getQuantite());
            vente.setPrixUnitaire(venteDetails.getPrixUnitaire());
            vente.setTotal(venteDetails.getQuantite() * venteDetails.getPrixUnitaire());

            vente.setDateVente(venteDetails.getDateVente());
            vente.setDateLivraison(venteDetails.getDateLivraison());
            vente.setDatePaiement(venteDetails.getDatePaiement());
            vente.setStatut(venteDetails.getStatut());
            vente.setNotes(venteDetails.getNotes());

            if (venteDetails.getRecolte() != null) {
                recolteRepository.findById(venteDetails.getRecolte().getId())
                        .ifPresent(vente::setRecolte);
            }

            return venteRepository.save(vente);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteVente(@PathVariable Long id) {
        venteRepository.deleteById(id);
    }
}
