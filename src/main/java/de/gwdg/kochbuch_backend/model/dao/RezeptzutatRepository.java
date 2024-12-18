package de.gwdg.kochbuch_backend.model.dao;



import de.gwdg.kochbuch_backend.model.dto.Rezeptzutat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RezeptzutatRepository extends CrudRepository<Rezeptzutat, Long>{

    Optional<Rezeptzutat> findById(Long id);

    // Find all Rezeptzutaten for a given Rezept ID
    List<Rezeptzutat> findAllByRezeptId(int rezeptId);
}
