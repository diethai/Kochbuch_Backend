package de.gwdg.kochbuch_backend.controller;

import com.itextpdf.io.exceptions.IOException;
import de.gwdg.kochbuch_backend.model.dto.Rezept;
import de.gwdg.kochbuch_backend.service.RezeptService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rezepte") // Basis-URL für alle Endpunkte
public class RezeptController {

    private final RezeptService rezeptService;

    @Autowired
    public RezeptController(RezeptService rezeptService) {
        this.rezeptService = rezeptService;
    }

    // Create: Neues Rezept erstellen
    @PostMapping
    public ResponseEntity<Rezept> createRezept(@RequestBody Rezept rezept) {
        Rezept neuesRezept = rezeptService.createRezept(rezept);
        return new ResponseEntity<>(neuesRezept, HttpStatus.CREATED); // 201 Created
    }

    // Read: Alle Rezepte abrufen
    @GetMapping
    public ResponseEntity<List<Rezept>> getAllRezepte() {
        List<Rezept> rezepte = rezeptService.getAllRezepte();
        return new ResponseEntity<>(rezepte, HttpStatus.OK); // 200 OK
    }

    // Read: Einzelnes Rezept nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Rezept> getRezeptByID(@PathVariable Long id) {
        try {
            Rezept rezept = rezeptService.getRezeptByID(id);
            return new ResponseEntity<>(rezept, HttpStatus.OK); // 200 OK
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // Update: Rezept aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<Rezept> updateRezept(@PathVariable Long id, @RequestBody Rezept rezept) {
        try {
            rezept.setId(id); // Setze die ID auf das übergebene ID-Path-Variable
            Rezept aktualisiertesRezept = rezeptService.updateRezept(rezept);
            return new ResponseEntity<>(aktualisiertesRezept, HttpStatus.OK); // 200 OK
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // Delete: Rezept löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRezept(@PathVariable Long id) {
        try {
            rezeptService.deleteRezept(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // Create: Mehrere Rezepte auf einmal erstellen
    @Transactional
    @PostMapping("/multiple")
    public ResponseEntity<List<Rezept>> createRezepte(@RequestBody List<Rezept> rezepte) {
        List<Rezept> neueRezepte = rezeptService.createRezepte(rezepte);
        return new ResponseEntity<>(neueRezepte, HttpStatus.CREATED); // 201 Created
    }

    @GetMapping("/author/{authorName}")
    public ResponseEntity<Optional<Rezept>> getRezepteByAuthor(@PathVariable long id) {
        Optional<Rezept> rezepte = rezeptService.getRezepteDesAutors(id);
        if (rezepte.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found, falls keine Rezepte gefunden wurden
        }
        return new ResponseEntity<>(rezepte, HttpStatus.OK); // 200 OK
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generateRezeptPdf(@PathVariable Long id) {
        try {
            // Aufruf der Methode zur PDF-Erstellung
            byte[] pdfBytes = rezeptService.generateRezeptPdf(id);

            // PDF als Byte-Array zurückgeben, mit passenden HTTP-Headern
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "inline; filename=rezept_" + id + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK); // 200 OK mit PDF im Body
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found, wenn das Rezept nicht existiert
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error bei Fehlern
        }
    }
}
