package de.gwdg.kochbuch_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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

    @Column(name = "gramm")
    private long gramm;

    @ManyToMany(mappedBy = "rezeptzutaten") // Bidirektionale Beziehung
    @JsonBackReference
    private List<Rezept> rezepte;

    @Column(name = "ml")
    private long ml;

    private String BildURL;
}

