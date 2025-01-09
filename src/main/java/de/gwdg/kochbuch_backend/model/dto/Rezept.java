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

import java.util.List;

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
    @Column(length = 10000)
    private String beschreibung;

    @ManyToMany
    @JoinTable(
            name = "rezept_rezeptzutat",  // Name der Join-Tabelle
            joinColumns = @JoinColumn(name = "rezept_id"), // Spalte in der Join-Tabelle für Rezept
            inverseJoinColumns = @JoinColumn(name = "rezeptzutat_id") // Spalte in der Join-Tabelle für Rezeptzutat
    )
    private List<Rezeptzutat> rezeptzutaten;

    @ManyToOne // mehrere Rezepte können zu einem Autor gehören
    @JoinColumn(name = "autorID", referencedColumnName = "id")
    private Autor autor;

    private double zubereitungszeit; // in Minuten

    private String BildURL;
}
