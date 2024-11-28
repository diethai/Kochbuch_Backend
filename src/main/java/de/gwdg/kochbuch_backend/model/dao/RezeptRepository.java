package de.gwdg.kochbuch_backend.model.dao;


import de.gwdg.kochbuch_backend.model.dto.Rezept;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RezeptRepository extends CrudRepository {

    Optional<Rezept> findById(Long id);
    Optional<Rezept> findByName(String name);
}
