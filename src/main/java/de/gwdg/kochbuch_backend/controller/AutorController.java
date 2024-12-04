package de.gwdg.kochbuch_backend.controller; // Pfad der Datei in welchem package wir uns befinden

import de.gwdg.kochbuch_backend.model.dto.Autor; // import des dto Autor aus dem Ordner model
import de.gwdg.kochbuch_backend.service.AutorService; // import des AutorService aus dem Ordner service
import jakarta.validation.Valid; // wird verwendet, um die Validierung von Objekten zu ermöglichen.
import org.springframework.beans.factory.annotation.Autowired; //wird verwendet, um die Autowiring-Funktion von Spring zu ermöglichen.
import org.springframework.http.HttpStatus; // wird verwendet, um die HTTP-Status-Codes von Spring zu ermöglichen.
import org.springframework.http.ResponseEntity; // wird verwendet, um die ResponseEntity-Klasse von Spring zu ermöglichen.
import org.springframework.web.bind.annotation.*; // wird verwendet, um alle Annotationen von Spring Web zu ermöglichen.

import java.util.List; // Diese Klasse wird verwendet, um eine Liste von Objekten zu erstellen.

@RestController                                             /* Hier wird die AutorController-Klasse definiert. */
@RequestMapping("/api/autoren")                          /* Die @RestController-Annotation wird verwendet, um die Klasse als Controller zu markieren. */
public class AutorController {                             /* Die @RequestMapping-Annotation wird verwendet, um die URL /api/autoren als Endpunkt für die Anfragen zu markieren. */

    private final AutorService autorService;               /* Hier wird die AutorService-Instanz als Feld der AutorController-Klasse definiert. */

    @Autowired                                             /* Hier wird die Autowiring-Funktion von Spring verwendet, um die AutorService-Instanz zu injectieren. */
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // Erstelle einen neuen Autor
    @PostMapping                                                                                             /* Hier wird die @PostMapping-Annotation verwendet, um die Methode als Endpunkt für die POST-Anfragen zu markieren. */
    public ResponseEntity<Autor> createAutor(@Valid @RequestBody Autor autor) {                                 /* Hier wird die @Valid-Annotation verwendet, um die Validierung des Autor-Objekts zu ermöglichen. */
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.createAutor(autor));                 /* Hier wird die ResponseEntity-Klasse verwendet, um die Antwort auf die Anfrage zu erstellen. */
    }

    // Lies alle Autoren
    @GetMapping                                                                                              /* Hier wird die @GetMapping-Annotation verwendet, um die Methode als Endpunkt für die GET-Anfragen zu markieren. */
    public ResponseEntity<List<Autor>> getAllAutoren() {                                                        /* Hier wird die ResponseEntity-Klasse verwendet, um die Antwort auf die Anfrage zu erstellen. */
        return ResponseEntity.ok(autorService.getAllAutoren());                                                 /* Hier wird die AutorService-Instanz verwendet, um die Liste der Autoren zu erhalten. */
    }

    // Lies einen Autor mit der spezifischen ID
    @GetMapping("/{id}")                                                                                    /* @GetMapping-Annotation wird verwendet, um die Methode als Endpunkt für die GET-Anfragen zu markieren. */
    public ResponseEntity<Autor> getAutorByID(@PathVariable Long id) {                                        /* @PathVariable-Annotation wird verwendet, um den id-Parameter als Pfad-Parameter zu markieren. */
        try {                                                                                                 /* try-catch-Block wird verwendet, um die Ausnahme zu fangen, wenn der Autor nicht gefunden wird. */
            return ResponseEntity.ok(autorService.getAutorByID(id));                                          /* AutorService-Instanz verwendet, um den Autor zu erhalten. */
        } catch (Exception e) {                                                                               /* Ausnahme abgefangen, wenn der Autor nicht gefunden wird. */
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();                                       /* ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen. */
        }
    }

    // Aktualisiere einen Autor
    @PutMapping("/{id}")                                                                                  /* @PutMapping-Annotation wird verwendet, um die Methode als Endpunkt für die PUT-Anfragen zu markieren. */
    public ResponseEntity<Autor> updateAutor(@PathVariable Long id, @Valid @RequestBody Autor autor) {      /* @Valid-Annotation wird verwendet, um die Validierung des Autor-Objekts zu ermöglichen. */
        try {                                                                                               /* try-catch-Block wird verwendet, um die Ausnahme zu fangen, wenn der Autor nicht gefunden wird. */
            Autor existingAutor = autorService.getAutorByID(id);                                            /* AutorService-Instanz wird verwendet, um den Autor zu erhalten. */
            existingAutor.setAutorName(autor.getAutorName());                                               /* Autor-Name des Autor-Objekts wird aktualisiert. */
            return ResponseEntity.ok(autorService.updateAutor(existingAutor));                              /* AutorService-Instanz wird verwendet, um den Autor zu aktualisieren. */
        } catch (Exception e) {                                                                             /* Ausnahme wird gefangen, wenn der Autor nicht gefunden wird. */
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();                                     /* ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen. */
        }
    }

    // Lösche einen Autor
    @DeleteMapping("/{id}")                                                                                 /* @DeleteMapping-Annotation wird verwendet, um die Methode als Endpunkt für die DELETE-Anfragen zu markieren. */
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {                                          /* @PathVariable-Annotation wird verwendet, um den id-Parameter als Pfad-Parameter zu markieren. */
        try {                                                                                                 /* try-catch-Block wird verwendet, um die Ausnahme zu fangen, wenn der Autor nicht gefunden wird. */
            autorService.deleteAutor(id);                                                                     /* AutorService-Instanz wird verwendet, um den Autor zu löschen. */
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();                                      /* ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen. */
        } catch (Exception e) {                                                                               /* Ausnahme wird gefangen, wenn der Autor nicht gefunden wird. */
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();                                       /* ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen. */
        }
    }
}
