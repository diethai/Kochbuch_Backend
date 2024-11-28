package de.gwdg.kochbuch_backend.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rezept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Der Titel darf nicht leer sein")
    private String titel;

    @NotNull(message = "Die Beschreibung darf nicht leer sein")
    @Column(length = 5000)
    private String beschreibung;

    @NotBlank(message = "Der Autor darf nicht leer sein")
    private String autor;

    private double zubereitungszeit; // in Minuten
}
