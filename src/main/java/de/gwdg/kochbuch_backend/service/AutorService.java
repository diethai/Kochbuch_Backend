package de.gwdg.kochbuch_backend.service;

import de.gwdg.kochbuch_backend.model.dao.AutorRepository;
import de.gwdg.kochbuch_backend.model.dto.Autor;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    //Create: nimmt ein Autor Objekt entgegen und speichert dieses in die Oracle Datenbank
    @Transactional //Datenbank-Operation können nicht mehr gleichzeitig durchgeführt werden
    public Autor createAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    //Read: liest alle Autor Objekte aus der Datenbank
    public List<Autor> getAllAutoren() {
        return (List<Autor>) autorRepository.findAll();
    }

    // liest den Autor mit der spezifischen ID aus
    public Autor getAutorByID(Long id) throws EntityNotFoundException {
        return autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor mit ID " + id + " nicht gefunden"));
    }

    //Update: updated das übergebene Autor Objekt
    public Autor updateAutor(Autor autor) {
        // Prüfen, ob der Autor mit der ID existiert
        Autor existingAutor = autorRepository.findById(autor.getId())
                .orElseThrow(() -> new EntityNotFoundException("Autor mit ID " + autor.getId() + " nicht gefunden"));

        // Felder aktualisieren
        existingAutor.setAutorName(autor.getAutorName());


        // Speichern der Änderungen
        return (Autor) autorRepository.save(existingAutor);
    }

    //Delete: löscht einen Autor anhand seiner ID aus der Datenbank
    public void deleteAutor(Long id) throws EntityNotFoundException {

        // Prüfen, ob der Autor mit der ID existiert
        if (!autorRepository.existsById(id)) {
            throw new EntityNotFoundException("Autor mit ID " + id + " nicht gefunden");
        }
        // Löschen des Autors
        autorRepository.deleteById(id);
    }

}
