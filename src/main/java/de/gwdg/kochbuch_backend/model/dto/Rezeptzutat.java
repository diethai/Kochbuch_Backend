package de.gwdg.kochbuch_backend.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rezeptzutaten")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rezeptzutat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "ZutatName darf nicht leer sein")
    @Column(name = "zutat_name", nullable = false)
    private String zutatName;

    @ManyToMany(mappedBy = "rezeptzutaten") // Diese Beziehung wird vom "Rezept" verwaltet
    private List<Rezept> rezepte;

    @Positive(message = "Gramm-Wert muss positiv sein")
    @Column(name = "gramm", nullable = true)
    private long gramm;

    @Positive(message = "Milliliter-Wert muss positiv sein")
    @Column(name = "ml", nullable = true)
    private long ml;
}

