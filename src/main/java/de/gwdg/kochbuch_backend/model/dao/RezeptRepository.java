package de.gwdg.kochbuch_backend.model.dao;


import de.gwdg.kochbuch_backend.model.dto.Rezept;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RezeptRepository extends CrudRepository<Rezept, Long> {

    Optional<Rezept> findById(Long id);


}
