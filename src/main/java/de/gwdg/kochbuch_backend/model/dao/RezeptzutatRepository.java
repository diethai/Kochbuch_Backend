package de.gwdg.kochbuch_backend.model.dao;



import de.gwdg.kochbuch_backend.model.dto.Rezeptzutat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface RezeptzutatRepository extends CrudRepository<Rezeptzutat, Long>{

    Optional<Rezeptzutat> findById(Long id);

    // Finde alle Rezeptzutaten, die zu einem bestimmten Rezept gehören
    List<Rezeptzutat> findAllByRezepte_Id(Long rezeptId);
}
