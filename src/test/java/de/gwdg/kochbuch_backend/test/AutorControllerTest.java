// Paketdeklaration: Definiert das Paket, in dem sich die Testklasse befindet
package de.gwdg.kochbuch_backend.test;

// Importieren von Klassen und Paketen, die für den Test benötigt werden
import de.gwdg.kochbuch_backend.controller.AutorController; // Controller, der getestet wird
import de.gwdg.kochbuch_backend.model.dao.AutorRepository;
import de.gwdg.kochbuch_backend.model.dto.Autor; // DTO-Klasse für Autor
import de.gwdg.kochbuch_backend.service.AutorService; // Service-Klasse, die vom Controller verwendet wird
import de.gwdg.kochbuch_backend.setup.builder.AutorOptionsObject; // Hilfsklasse zum Erstellen von Testobjekten

import org.junit.jupiter.api.DisplayName; // Ermöglicht benutzerfreundliche Testnamen
import org.junit.jupiter.api.Test; // Annotation zum Markieren von Testmethoden
import org.springframework.beans.factory.annotation.Autowired; // Injection von abhängigen Komponenten
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs; // Aktiviert Spring REST Docs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // Testkonfiguration für Web-Controller
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType; // Stellt HTTP-Medientypen bereit
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders; // Baut HTTP-Anfragen für REST Docs
import org.springframework.restdocs.payload.FieldDescriptor; // Beschreibt die Felder in API-Dokumentationen
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Mocking von Beans mit Mockito
import org.springframework.test.web.servlet.MockMvc; // Ermöglicht das Testen von HTTP-Anfragen

import java.util.List; // Java-Klasse für Listen

// Statische Importe für Mockito und REST Docs, die den Code lesbarer machen
import static org.mockito.BDDMockito.given; // Stellt Methoden für Verhalten von Mock-Objekten bereit
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document; // Dokumentiert Anfragen
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*; // Preprocessing von Anfragen/Antworten
import static org.springframework.restdocs.payload.PayloadDocumentation.*; // Dokumentiert die Nutzlast
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Überprüfung von HTTP-Statuscodes

// Testklasse, die Web-Controller von Spring Boot testet
//@WebMvcTest(AutorController.class) // Fokussiert sich nur auf den AutorController
//@AutoConfigureRestDocs // Aktiviert automatische REST-Dokumentation
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest // lädt mehr wie WebMvcTest ?
public class AutorControllerTest {

    // Konstanten für die Beschreibungen von Feldern in der Dokumentation
    private static final String FIELD_DESC_ID = "User ID"; // Beschreibung des Felds 'id'
    private static final String FIELD_DESC_AUTORNAME = "Autor Name"; // Beschreibung des Felds 'autorName'

    // Basispfad für die generierten Dokumentationen
    private static final String DOCUMENT_PATH = "api/autoren/";

    // MockMvc wird verwendet, um HTTP-Anfragen an den Controller zu simulieren
    @Autowired
    private MockMvc mockMvc;

    // Mock-Service, der in den Tests verwendet wird, um den echten Service zu ersetzen
    @MockitoBean
    private AutorService autorService;

    // Feldbeschreibung für ein einzelnes Autor-Objekt
    private final FieldDescriptor[] autorDescriptor = new FieldDescriptor[]{
            fieldWithPath("id").description(FIELD_DESC_ID), // Dokumentiert das Feld 'id'
            fieldWithPath("autorName").description(FIELD_DESC_AUTORNAME) // Dokumentiert das Feld 'autorName'
    };

    // Feldbeschreibung für eine Liste von Autor-Objekten
    private final FieldDescriptor[] autorListDescriptor = new FieldDescriptor[]{
            fieldWithPath("[].id").description(FIELD_DESC_ID), // Dokumentiert das Feld 'id' in einer Liste
            fieldWithPath("[].autorName").description(FIELD_DESC_AUTORNAME) // Dokumentiert 'autorName' in einer Liste
    };

    // Testmethode, die überprüft, ob alle Autoren korrekt abgerufen werden
    @Test
    @DisplayName("Find all Autors") // Freundlicher Testname
    void findAllAutorsTest() throws Exception {
        String url = "/api/autoren/getAll"; // URL des Endpunkts, der getestet wird

        // Erstellung einer Liste von Autor-Objekten für den Test
        List<Autor> autoren = List.of(
                AutorOptionsObject.autor().withId(1).withAutorName("maria H").build(), // Autor mit ID 1
                AutorOptionsObject.autor().withId(2).withAutorName("Max P").build() // Autor mit ID 2
        );

        // Konfiguration des Mock-Services: Gibt die Testdaten zurück, wenn die Methode aufgerufen wird
        given(autorService.getAllAutoren()).willReturn(autoren);

        // Simuliert eine HTTP-GET-Anfrage an den Endpunkt und überprüft die Antwort
        this.mockMvc
                .perform(RestDocumentationRequestBuilders // Baut die HTTP-GET-Anfrage
                        .get(url) // Ziel-URL des Endpunkts
                        .accept(MediaType.APPLICATION_JSON)) // Erwartet JSON als Antwort
                .andExpect(status().isOk()) // Überprüft, ob der HTTP-Statuscode 200 (OK) ist
                .andDo(document(DOCUMENT_PATH + "findAll", // Dokumentiert die Anfrage
                        preprocessRequest(prettyPrint()), // Formatiert die Anfrage in der Dokumentation
                        preprocessResponse(prettyPrint()), // Formatiert die Antwort in der Dokumentation
                        responseFields(autorListDescriptor) // Dokumentiert die Felder der Antwort
                ));
    }
}
