package de.gwdg.kochbuch_backend.test;

import de.gwdg.kochbuch_backend.controller.AutorController;
import de.gwdg.kochbuch_backend.model.dto.Autor;
import de.gwdg.kochbuch_backend.service.AutorService;

import de.gwdg.kochbuch_backend.setup.builder.AutorOptionsObject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AutorController.class)
@AutoConfigureRestDocs
public class AutorControllerTest {

    private static final String FIELD_DESC_ID = "User ID";
    private static final String FIELD_DESC_AUTORNAME = "Autor Name";

    private static final String DOCUMENT_PATH = "api/autoren/";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AutorService autorService;

    private final FieldDescriptor[] autorDescriptor = new FieldDescriptor[]{
            fieldWithPath("id").description(FIELD_DESC_ID),
            fieldWithPath("autorName").description(FIELD_DESC_AUTORNAME),

    };

    private final FieldDescriptor[] autorListDescriptor = new FieldDescriptor[]{
            fieldWithPath("[].id").description(FIELD_DESC_ID),
            fieldWithPath("[].autorName").description(FIELD_DESC_AUTORNAME),

    };

    @Test
    @DisplayName("Find all Autors")
    void findAllAutorsTest() throws Exception {
        String url = "/api/autoren/getAll";

        // Erstelle eine Liste von Autor-Objekten mit AutorTestObjects
        List<Autor> autoren = List.of(
                AutorOptionsObject.autor().withId(1).withAutorName("maria H").build(),
                AutorOptionsObject.autor().withId(2).withAutorName("Max P").build()
        );

        // Mock den Service
        given(autorService.getAllAutoren()).willReturn(autoren);

        // Teste den Endpunkt und dokumentiere ihn mit REST Docs
        this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(DOCUMENT_PATH + "findAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(autorListDescriptor)
                ));
    }


}
