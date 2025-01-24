package de.gwdg.kochbuch_backend.controller;

import de.gwdg.kochbuch_backend.model.dto.Rezept;
import de.gwdg.kochbuch_backend.service.RezeptService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    /*
     * Endpunkt zum Erstellen eines neuen Rezepts.
     * Dieser Endpoint erwartet ein Rezept im Request-Body und erstellt es.
     * Gibt das neu erstellte Rezept zurück.
     */
    @PostMapping("/create")
    public ResponseEntity<Rezept> createRezept(@RequestBody Rezept rezept) {
        Rezept neuesRezept = rezeptService.createRezept(rezept);
        return new ResponseEntity<>(neuesRezept, HttpStatus.CREATED); // 201 Created
    }

    /*
     * Endpunkt zum Abrufen aller Rezepte.
     * Dieser Endpoint gibt eine Liste aller Rezepte zurück, die in der Datenbank gespeichert sind.
     * Gibt eine Liste von Rezepten zurück.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Rezept>> getAllRezepte() {
        List<Rezept> rezepte = rezeptService.getAllRezepte();
        return new ResponseEntity<>(rezepte, HttpStatus.OK); // 200 OK
    }

    /*
     * Endpunkt zum Abrufen eines einzelnen Rezepts anhand seiner ID.
     * Dieser Endpoint gibt das Rezept mit der angegebenen ID zurück.
     * Wenn kein Rezept mit dieser ID gefunden wird, wird ein 404-Fehler zurückgegeben.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Rezept> getRezeptByID(@PathVariable Long id) {
        try {
            Rezept rezept = rezeptService.getRezeptById(id);
            return new ResponseEntity<>(rezept, HttpStatus.OK); // 200 OK
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    /*
     * Endpunkt zum Aktualisieren eines bestehenden Rezepts.
     * Dieser Endpoint erwartet ein Rezept im Request-Body, aktualisiert es und gibt das aktualisierte Rezept zurück.
     * Wenn das Rezept nicht gefunden wird, wird ein 404-Fehler zurückgegeben.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Rezept> updateRezept(@PathVariable Long id, @RequestBody Rezept rezept) {
        try {
            rezept.setId(id); // Setze die ID auf das übergebene ID-Path-Variable
            Rezept aktualisiertesRezept = rezeptService.updateRezept(rezept);
            return new ResponseEntity<>(aktualisiertesRezept, HttpStatus.OK); // 200 OK
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    /*
     * Endpunkt zum Löschen eines Rezepts anhand seiner ID.
     * Dieser Endpoint löscht das Rezept mit der angegebenen ID.
     * Gibt eine 204-Response zurück, wenn das Löschen erfolgreich war, oder einen 404-Fehler, wenn das Rezept nicht gefunden wurde.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRezept(@PathVariable Long id) {
        try {
            rezeptService.deleteRezept(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    /*
     * Endpunkt zum Erstellen mehrerer Rezepte auf einmal.
     * Dieser Endpoint erwartet eine Liste von Rezepten im Request-Body und erstellt diese.
     * Gibt die Liste der neu erstellten Rezepte zurück.
     */
    @Transactional
    @PostMapping("/multiple")
    public ResponseEntity<List<Rezept>> createRezepte(@RequestBody List<Rezept> rezepte) {
        List<Rezept> neueRezepte = rezeptService.createRezepte(rezepte);
        return new ResponseEntity<>(neueRezepte, HttpStatus.CREATED); // 201 Created
    }

    /*
     * Endpunkt zum Abrufen der Rezepte eines bestimmten Autors.
     * Dieser Endpoint gibt alle Rezepte eines bestimmten Autors zurück.
     * Falls keine Rezepte für diesen Autor gefunden werden, wird ein 404-Fehler zurückgegeben.
     */
    @GetMapping("/author/{authorName}")
    public ResponseEntity<Optional<Rezept>> getRezepteByAuthor(@PathVariable long id) {
        Optional<Rezept> rezepte = rezeptService.getRezepteDesAutors(id);
        if (rezepte.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found, falls keine Rezepte gefunden wurden
        }
        return new ResponseEntity<>(rezepte, HttpStatus.OK); // 200 OK
    }

    /*
     * Endpunkt zum Generieren einer PDF für ein Rezept.
     * Dieser Endpoint ruft die Methode zur Erstellung einer PDF für das Rezept mit der angegebenen ID auf.
     * Gibt die PDF als Antwort im Body zurück, zusammen mit den passenden Headern für den Dateityp.
     */
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generateRezeptPdf(@PathVariable Long id) {
        try {
            // PDF-Daten für das Rezept generieren
            byte[] pdfBytes = rezeptService.generateRezeptPdf(id);

            // HTTP-Header für die PDF-Antwort setzen
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "rezept_" + id + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK); // Erfolgreiche Antwort mit PDF
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Rezept nicht gefunden
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Allgemeiner Fehler
        }
    }

}
