package de.gwdg.kochbuch_backend.model.dao;

import de.gwdg.kochbuch_backend.model.dto.Autor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AutorRepository extends CrudRepository<Autor, Long> {

    Optional<Autor> findById(Long id);

}
