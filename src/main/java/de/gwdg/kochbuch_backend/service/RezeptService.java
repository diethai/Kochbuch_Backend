package de.gwdg.kochbuch_backend.service;

import de.gwdg.kochbuch_backend.model.dao.RezeptRepository;
import de.gwdg.kochbuch_backend.model.dto.Rezept;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RezeptService {
    private final RezeptRepository rezeptRepository;

    @Autowired
    public RezeptService(RezeptRepository rezeptRepository) {
        this.rezeptRepository = rezeptRepository;
    }

    //Create: nimmt ein Rezept Objekt entgegen und speichert dieses in die Oracle Datenbank
    public Rezept createRezept(Rezept rezept) {
        return (Rezept) rezeptRepository.save(rezept);
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
        existingRezept.setAutor(rezept.getAutor());
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

}
