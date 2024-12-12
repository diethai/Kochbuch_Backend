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
@Table(name = "rezeptzutaten")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rezeptzutat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String zutatName;

    @ManyToOne
    @JoinColumn(name = "rezept_id", nullable = false)
    private Rezept rezept;
}

