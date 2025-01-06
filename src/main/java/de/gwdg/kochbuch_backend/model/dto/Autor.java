package de.gwdg.kochbuch_backend.model.dto; /* Package-Name der Klasse */

import jakarta.persistence.Entity; /* Import der Entity-Klasse */
import jakarta.persistence.Id; /* Import der Id-Klasse */
import jakarta.persistence.GeneratedValue; /* Import der GeneratedValue-Klasse */
import jakarta.persistence.GenerationType; /* Import der GenerationType-Klasse */
import jakarta.persistence.*; /* Import der Persistenz-Annotatoren */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor; /* Import der Lombok-Annotatoren */
import lombok.Getter; /* Import der Getter-Annotatoren */
import lombok.NoArgsConstructor; /* Import der NoArgsConstructor-Annotatoren */
import lombok.Setter; /* Import der Setter-Annotatoren */

import java.util.List;

@Entity                      /* Die Klasse ist eine Persistenz-Klasse */
@Table (name = "autoren")    /* Die Tabelle wird als "autoren" benannt */
@Getter                      /* Die Getter-Methode wird generiert */
@Setter                      /* Die Setter-Methode wird generiert */
@NoArgsConstructor           /* Der Konstruktor ohne Parameter wird generiert */
@AllArgsConstructor          /* Der Konstruktor mit allen Feldern wird generiert */
public class Autor {         /* Die Klasse "Autor" wird definiert */

    @Id /* Die ID ist eindeutig */
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* Die ID wird automatisch generiert */
    private long id; /* Die ID ist eine Ganzzahl */

    @NotNull(message = "Autorname darf nicht null sein")
    private String autorName; /* Der Name des Autors ist eine Zeichenkette */

}


