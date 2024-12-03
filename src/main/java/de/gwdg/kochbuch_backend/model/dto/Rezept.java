package de.gwdg.kochbuch_backend.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "rezepte") // specify the name of the table
@Getter // automatisch erstellte get-Methods
@Setter // automatisch erstellte set-Methods
@NoArgsConstructor
@AllArgsConstructor
public class Rezept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Der Titel darf nicht leer sein")
    private String titel;

    @NotNull(message = "Die Beschreibung darf nicht leer sein")
    @Column(length = 150)
    private String beschreibung;

    @ManyToOne // mehrere Rezepte können zu einem Autor gehören
    @JoinColumn(name = "autorID", referencedColumnName = "id")
    private Autor autor;

    private double zubereitungszeit; // in Minuten
}
