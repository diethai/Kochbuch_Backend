package de.gwdg.kochbuch_backend.service;

import de.gwdg.kochbuch_backend.model.dao.RezeptRepository;
import de.gwdg.kochbuch_backend.model.dto.Rezept;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RezeptService {
    private final RezeptRepository rezeptRepository;

    @Autowired
    public RezeptService(RezeptRepository rezeptRepository) {
        this.rezeptRepository = rezeptRepository;
    }

    //Create: nimmt ein Rezept Objekt entgegen und speichert dieses in die Oracle Datenbank
    @Transactional //Datenbank-Operation können nicht mehr gleichzeitig durchgeführt werden
    public Rezept createRezept(Rezept rezept) {
        return rezeptRepository.save(rezept);
    }

    //Read: liest alle Rezept Objekte aus der Datenbank
    public List<Rezept> getAllRezepte() {
        return (List<Rezept>) rezeptRepository.findAll();
    }

    // liest das Rezept mit der spezifischen ID aus
    public Rezept getRezeptByID(Long id) throws EntityNotFoundException {
        return rezeptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rezept mit ID " + id + " nicht gefunden"));
    }

    //Update: updated das übergebene Rezept Objekt
    public Rezept updateRezept(Rezept rezept) {
        // Prüfen, ob das Rezept mit der ID existiert
        Rezept existingRezept = rezeptRepository.findById(rezept.getId())
                .orElseThrow(() -> new EntityNotFoundException("Rezept mit ID " + rezept.getId() + " nicht gefunden"));

        // Felder aktualisieren
        existingRezept.setTitel(rezept.getTitel());
        existingRezept.setBeschreibung(rezept.getBeschreibung());
        existingRezept.setZubereitungszeit(rezept.getZubereitungszeit());

        // Speichern der Änderungen
        return (Rezept) rezeptRepository.save(existingRezept);
    }

    //Delete: löscht ein Rezept anhand seiner ID aus der Datenbank
    public void deleteRezept(Long id) throws EntityNotFoundException {

        // Prüfen, ob das Rezept mit der ID existiert
        if (!rezeptRepository.existsById(id)) {
            throw new EntityNotFoundException("Rezept mit ID " + id + " nicht gefunden");
        }
        // Löschen des Rezepts
        rezeptRepository.deleteById(id);
    }

    // Create: mehrere Rezepte auf einmal erstellen
    @Transactional
    public List<Rezept> createRezepte(List<Rezept> rezepte) {
        return (List<Rezept>) rezeptRepository.saveAll(rezepte);
    }

    // Liest alle Rezepte eines Autors aus der Datenbank
    public Optional<Rezept> getRezepteDesAutors(Long autorId) {
        // Rufe die Methode aus dem RezeptRepository auf, um alle Rezepte des Autors zu erhalten
        return rezeptRepository.findById(autorId);
    }

}
