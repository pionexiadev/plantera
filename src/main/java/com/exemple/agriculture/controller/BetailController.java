package com.exemple.agriculture.controller;

import com.exemple.agriculture.model.Betail;
import com.exemple.agriculture.repository.BetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/betail")
@CrossOrigin(origins = "*")
public class BetailController {

    @Autowired
    private BetailRepository betailRepository;

    // 🔍 Tous les animaux
    @GetMapping
    public List<Betail> getAllBetail() {
        return betailRepository.findAll();
    }

    // 🔍 Un animal par ID
    @GetMapping("/{id}")
    public Betail getBetailById(@PathVariable Long id) {
        return betailRepository.findById(id).orElse(null);
    }

    // ➕ Ajouter un animal
    @PostMapping
    public Betail createBetail(@RequestBody Betail betail) {
        return betailRepository.save(betail);
    }

    // ✏️ Modifier un animal
    @PutMapping("/{id}")
    public Betail updateBetail(@PathVariable Long id, @RequestBody Betail updatedBetail) {
        Betail existing = betailRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setNom(updatedBetail.getNom());
            existing.setType(updatedBetail.getType());
            existing.setRace(updatedBetail.getRace());
            existing.setDateNaissance(updatedBetail.getDateNaissance());
            existing.setPoids(updatedBetail.getPoids());
            existing.setNotes(updatedBetail.getNotes());
            return betailRepository.save(existing);
        }
        return null;
    }

    // ❌ Supprimer un animal
    @DeleteMapping("/{id}")
    public void deleteBetail(@PathVariable Long id) {
        betailRepository.deleteById(id);
    }

    // 🔍 Rechercher par type (ex: vache, mouton…)
    @GetMapping("/type/{type}")
    public List<Betail> getBetailByType(@PathVariable String type) {
        return betailRepository.findAll().stream()
                .filter(b -> b.getType().equalsIgnoreCase(type))
                .toList();
    }
}
