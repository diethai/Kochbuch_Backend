package de.gwdg.kochbuch_backend.service;

import de.gwdg.kochbuch_backend.model.dao.RezeptzutatRepository;
import de.gwdg.kochbuch_backend.model.dto.Rezeptzutat;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RezeptzutatService {
    private final RezeptzutatRepository rezeptzutatRepository;

    @Autowired
    public RezeptzutatService(RezeptzutatRepository rezeptzutatRepository) {
        this.rezeptzutatRepository = rezeptzutatRepository;
    }

    // Create: fügt eine neue Rezeptzutat hinzu
    @Transactional
    public Rezeptzutat createRezeptzutat(Rezeptzutat rezeptzutat) {
        return rezeptzutatRepository.save(rezeptzutat);
    }

    // Read: liest alle Rezeptzutaten aus der Datenbank
    public List<Rezeptzutat> getAllRezeptzutaten() {
        return (List<Rezeptzutat>) rezeptzutatRepository.findAll();
    }

    // Read: liest eine spezifische Rezeptzutat anhand ihrer ID
    public Rezeptzutat getRezeptzutatByID(long id) throws EntityNotFoundException {
        return rezeptzutatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rezeptzutat mit ID " + id + " nicht gefunden"));
    }

    // Update: aktualisiert eine vorhandene Rezeptzutat
    @Transactional
    public Rezeptzutat updateRezeptzutat(Rezeptzutat rezeptzutat) {
        Rezeptzutat existingRezeptzutat = rezeptzutatRepository.findById(rezeptzutat.getId())
                .orElseThrow(() -> new EntityNotFoundException("Rezeptzutat mit ID " + rezeptzutat.getId() + " nicht gefunden"));

        // Felder aktualisieren
        existingRezeptzutat.setZutatName(rezeptzutat.getZutatName());
        existingRezeptzutat.setGramm(rezeptzutat.getGramm());
        existingRezeptzutat.setMl(rezeptzutat.getMl());
        existingRezeptzutat.setRezept(rezeptzutat.getRezept());

        // Speichern der Änderungen
        return rezeptzutatRepository.save(existingRezeptzutat);
    }

    // Delete: löscht eine Rezeptzutat anhand ihrer ID
    @Transactional
    public void deleteRezeptzutat(long id) throws EntityNotFoundException {
        if (!rezeptzutatRepository.existsById(id)) {
            throw new EntityNotFoundException("Rezeptzutat mit ID " + id + " nicht gefunden");
        }
        rezeptzutatRepository.deleteById(id);
    }

    // Create: mehrere Rezeptzutaten auf einmal erstellen
    @Transactional
    public List<Rezeptzutat> createRezeptzutaten(List<Rezeptzutat> rezeptzutaten) {
        return (List<Rezeptzutat>) rezeptzutatRepository.saveAll(rezeptzutaten);
    }

    // Read: liest alle Rezeptzutaten für ein spezifisches Rezept aus
    public List<Rezeptzutat> getRezeptzutatenForRezept(int rezeptId) {
        return rezeptzutatRepository.findAllByRezeptId(rezeptId);
    }
}
