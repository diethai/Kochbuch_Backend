package de.gwdg.kochbuch_backend.model.dao; /* Hier wird der Package-Name angegeben, in dem sich die Datei befindet. */

import de.gwdg.kochbuch_backend.model.dto.Autor; /* Hier wird der Import des Autor-DTOs durchgeführt. */
import org.springframework.data.repository.CrudRepository; /* Hier wird der Import der CrudRepository-Klasse durchgeführt. */

import java.util.Optional; /* Hier wird der Import der Optional-Klasse durchgeführt. */

public interface AutorRepository extends CrudRepository<Autor, Long> { /* Hier wird die AutorRepository-Klasse definiert, die die CrudRepository-Klasse erweitert. */

    Optional<Autor> findById(Long id);                                 /* Hier wird die Methode findById definiert, die einen Autor mit der spezifischen ID zurückgibt. */
}
