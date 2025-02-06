package de.gwdg.kochbuch_backend.model.dao;


import de.gwdg.kochbuch_backend.model.dto.Rezept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RezeptRepository extends CrudRepository<Rezept, Long> {

    Optional<Rezept> findById(Long id);

}
