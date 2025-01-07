package de.gwdg.kochbuch_backend.controller;

import de.gwdg.kochbuch_backend.model.dto.Rezeptzutat;
import de.gwdg.kochbuch_backend.service.RezeptzutatService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rezeptzutaten") // Basis-URL für alle Endpunkte
public class RezeptzutatController {

    private final RezeptzutatService rezeptzutatService;

    @Autowired
    public RezeptzutatController(RezeptzutatService rezeptzutatService) {
        this.rezeptzutatService = rezeptzutatService;
    }

    // Create: Neue Rezeptzutat erstellen
    @PostMapping("/create")
    public ResponseEntity<Rezeptzutat> createRezeptzutat(@RequestBody Rezeptzutat rezeptzutat) {
        Rezeptzutat neueRezeptzutat = rezeptzutatService.createRezeptzutat(rezeptzutat);
        return new ResponseEntity<>(neueRezeptzutat, HttpStatus.CREATED); // 201 Created
    }

    // Read: Alle Rezeptzutaten abrufen
    @GetMapping("/getAll")
    public ResponseEntity<List<Rezeptzutat>> getAllRezeptzutaten() {
        List<Rezeptzutat> rezeptzutaten = rezeptzutatService.getAllRezeptzutaten();
        return new ResponseEntity<>(rezeptzutaten, HttpStatus.OK); // 200 OK
    }

    // Read: Einzelne Rezeptzutat nach ID abrufen
    @GetMapping("/get/{id}")
    public ResponseEntity<Rezeptzutat> getRezeptzutatByID(@PathVariable int id) {
        try {
            Rezeptzutat rezeptzutat = rezeptzutatService.getRezeptzutatByID(id);
            return new ResponseEntity<>(rezeptzutat, HttpStatus.OK); // 200 OK
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // Update: Rezeptzutat aktualisieren
    @PutMapping("/update/{id}")
    public ResponseEntity<Rezeptzutat> updateRezeptzutat(@PathVariable int id, @RequestBody Rezeptzutat rezeptzutat) {
        try {
            rezeptzutat.setId(id); // Setze die ID auf das übergebene ID-Path-Variable
            Rezeptzutat aktualisierteRezeptzutat = rezeptzutatService.updateRezeptzutat(rezeptzutat);
            return new ResponseEntity<>(aktualisierteRezeptzutat, HttpStatus.OK); // 200 OK
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // Delete: Rezeptzutat löschen
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRezeptzutat(@PathVariable int id) {
        try {
            rezeptzutatService.deleteRezeptzutat(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // Create: Mehrere Rezeptzutaten auf einmal erstellen
    @Transactional
    @PostMapping("/multiple")
    public ResponseEntity<List<Rezeptzutat>> createRezeptzutaten(@RequestBody List<Rezeptzutat> rezeptzutaten) {
        List<Rezeptzutat> neueRezeptzutaten = rezeptzutatService.createRezeptzutaten(rezeptzutaten);
        return new ResponseEntity<>(neueRezeptzutaten, HttpStatus.CREATED); // 201 Created
    }


}
