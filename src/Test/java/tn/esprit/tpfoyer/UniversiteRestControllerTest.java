package tn.esprit.tpfoyer;



import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UniversiteRestControllerTest.class)
class UniversiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;

    private Universite universite;

    @BeforeEach
    void setUp() {
        // Initialize a mock Universite object
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Université de Test");
        universite.setAdresse("123 Rue de l'Exemple");
        universite.setFoyer(null);  // Set Foyer if necessary or keep it null
    }

    @Test
    void testGetAllUniversites() throws Exception {
        List<Universite> universites = Arrays.asList(universite);
        Mockito.when(universiteService.retrieveAllUniversites()).thenReturn(universites);

        mockMvc.perform(get("/universites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idUniversite").value(universite.getIdUniversite()))
                .andExpect(jsonPath("$[0].nomUniversite").value(universite.getNomUniversite()))
                .andExpect(jsonPath("$[0].adresse").value(universite.getAdresse()));
    }

    @Test
    void testGetUniversiteById() throws Exception {
        Mockito.when(universiteService.retrieveUniversite(anyLong())).thenReturn(universite);

        mockMvc.perform(get("/universites/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()))
                .andExpect(jsonPath("$.nomUniversite").value(universite.getNomUniversite()))
                .andExpect(jsonPath("$.adresse").value(universite.getAdresse()));
    }

    @Test
    void testAddUniversite() throws Exception {
        Mockito.when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/universites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(universite)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()))
                .andExpect(jsonPath("$.nomUniversite").value(universite.getNomUniversite()))
                .andExpect(jsonPath("$.adresse").value(universite.getAdresse()));
    }

    @Test
    void testUpdateUniversite() throws Exception {
        Mockito.when(universiteService.modifyUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(put("/universites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()))
                .andExpect(jsonPath("$.nomUniversite").value(universite.getNomUniversite()))
                .andExpect(jsonPath("$.adresse").value(universite.getAdresse()));
    }

    @Test
    void testDeleteUniversite() throws Exception {
        Mockito.doNothing().when(universiteService).removeUniversite(anyLong());

        mockMvc.perform(delete("/universites/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testRetrieveUniversiteNotFound() throws Exception {
        Mockito.when(universiteService.retrieveUniversite(anyLong()))
                .thenThrow(new EntityNotFoundException("Universite not found"));

        mockMvc.perform(get("/universites/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    /**
     * Utility method to convert Java objects to JSON string.
     *
     * @param obj the object to convert
     * @return JSON string representation of the object
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper()
                    .findAndRegisterModules()
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
