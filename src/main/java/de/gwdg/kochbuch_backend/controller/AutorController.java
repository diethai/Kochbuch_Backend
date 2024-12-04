package de.gwdg.kochbuch_backend.controller; /* Package-Name der Klasse */

import de.gwdg.kochbuch_backend.model.dto.Autor; /* Import der Autor-Klasse */
import de.gwdg.kochbuch_backend.service.AutorService; /* Import der AutorService-Klasse */
import jakarta.persistence.EntityNotFoundException; /* Import der EntityNotFoundException-Klasse */
import jakarta.transaction.Transactional;
import jakarta.validation.Valid; /* Import der Valid-Annotation */
import org.springframework.beans.factory.annotation.Autowired; /* Import der Autowiring-Funktion von Spring */
import org.springframework.http.HttpStatus; /* Import der HttpStatus-Klasse */
import org.springframework.http.ResponseEntity; /* Import der ResponseEntity-Klasse */
import org.springframework.web.bind.annotation.*; /* Import der Annotationen von Spring Web */

import java.util.List; /* Import der List-Klasse */

@RestController                                                      /* Die Klasse ist ein Controller */
@RequestMapping("/api/autoren")                                    /* Die URL /api/autoren ist der Endpunkt für die Anfragen */
public class AutorController {                                       /* Die Klasse AutorController wird definiert */

    private final AutorService autorService;                         /* Die AutorService-Instanz wird als Feld der Klasse definiert */

    @Autowired                                                       /* Die Autowiring-Funktion von Spring wird verwendet, um die AutorService-Instanz zu injectieren */
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // Erstelle einen neuen Autor
    @PostMapping                                                                /* Die Methode ist der Endpunkt für die POST-Anfragen */
    public ResponseEntity<Autor> createAutor(@RequestBody Autor autor) {           /* Die @RequestBody-Annotation wird verwendet, um das Autor-Objekt als Körper der Anfrage zu markieren */
        Autor neuerAutor = autorService.createAutor(autor);                        /* Die AutorService-Instanz wird verwendet, um den neuen Autor zu erstellen */
        return new ResponseEntity<>(neuerAutor, HttpStatus.CREATED);               /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
    }

    // Read: Alle Autoren abrufen
    @GetMapping                                                                 /* Die Methode ist der Endpunkt für die GET-Anfragen */
    public ResponseEntity<List<Autor>> getAllAutoren() {                           /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
        List<Autor> autoren = autorService.getAllAutoren();                        /* Die AutorService-Instanz wird verwendet, um die Liste der Autoren zu erhalten */
        return new ResponseEntity<>(autoren, HttpStatus.OK);                       /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
    }

    // Read: Einzelnes Autor nach ID abrufen
    @GetMapping("/{id}")                                                         /* Die Methode ist der Endpunkt für die GET-Anfragen mit einem ID-Parameter */
    public ResponseEntity<Autor> getAutorByID(@PathVariable Long id) {              /* Der @PathVariable-Annotation wird verwendet, um den ID-Parameter zu markieren */
        try {                                                                       /* Ein try-catch-Block wird verwendet, um die Ausnahme zu fangen, wenn der Autor nicht gefunden wird */
            Autor autor = autorService.getAutorByID(id);                            /* Die AutorService-Instanz wird verwendet, um den Autor zu erhalten */
            return new ResponseEntity<>(autor, HttpStatus.OK);                      /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
        } catch (EntityNotFoundException e) {                                       /* Die Ausnahme wird gefangen, wenn der Autor nicht gefunden wird */
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);                      /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
        }
    }

    // Update: Autor aktualisieren
    @PutMapping("/{id}")                                                                                     /* Die Methode ist der Endpunkt für die PUT-Anfragen */
    public ResponseEntity<Autor> updateAutor(@PathVariable Long id, @Valid @RequestBody Autor autor) {          /* Der @PathVariable-Annotation wird verwendet, um den ID-Parameter zu markieren */
        try {                                                                                                   /* Ein try-catch-Block wird verwendet, um die Ausnahme zu fangen, wenn der Autor nicht gefunden wird */
            Autor aktualisiertAutor = autorService.updateAutor(autor);                                          /* Die AutorService-Instanz wird verwendet, um den Autor zu aktualisieren */
            return new ResponseEntity<>(aktualisiertAutor, HttpStatus.OK);                                      /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
        } catch (EntityNotFoundException e) {                                                                   /* Die Ausnahme wird gefangen, wenn der Autor nicht gefunden wird */
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);                                                  /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
        }
    }

    // Delete: Autor löschen
    @DeleteMapping("/{id}")                                                         /* Die Methode ist der Endpunkt für die DELETE-Anfragen */
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {                   /* Der @PathVariable-Annotation wird verwendet, um den ID-Parameter zu markieren */
        try {                                                                          /* Ein try-catch-Block wird verwendet, um die Ausnahme zu fangen, wenn der Autor nicht gefunden wird */
            autorService.deleteAutor(id);                                              /* Die AutorService-Instanz wird verwendet, um den Autor zu löschen */
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);                        /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
        } catch (EntityNotFoundException e) {                                          /* Die Ausnahme wird gefangen, wenn der Autor nicht gefunden wird */
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);                         /* Die ResponseEntity-Klasse wird verwendet, um die Antwort auf die Anfrage zu erstellen */
        }
    }

    // Um mehrere Autoren gleichzeitig der DB hinzuzufügen
    @PostMapping("/many")
    @Transactional
    public ResponseEntity<List<Autor>> createManyAutoren(@RequestBody List<Autor> autoren) {
        List<Autor> neueAutoren = autorService.createManyAutoren(autoren);
        return new ResponseEntity<>(neueAutoren, HttpStatus.CREATED);
    }
}
