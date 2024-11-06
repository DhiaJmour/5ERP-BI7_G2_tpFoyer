package tn.esprit.tpfoyer.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UniversiteRestController.class)
class UniversiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;

    private Universite universite;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        // Initialize mock Foyer object
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(200L);
        // Assuming Universite will set this Foyer
        // Set other fields if necessary

        // Initialize mock Universite object
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Université de Technologie");
        universite.setAdresse("123 Rue de l'Innovation, Paris");
        universite.setFoyer(foyer);
    }

    @Test
    void testGetUniversites() throws Exception {
        List<Universite> universites = Arrays.asList(universite);
        Mockito.when(universiteService.retrieveAllUniversites()).thenReturn(universites);

        mockMvc.perform(get("/universite/retrieve-all-universites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idUniversite").value(universite.getIdUniversite()))
                .andExpect(jsonPath("$[0].nomUniversite").value(universite.getNomUniversite()))
                .andExpect(jsonPath("$[0].adresse").value(universite.getAdresse()))
                .andExpect(jsonPath("$[0].foyer.idFoyer").value(universite.getFoyer().getIdFoyer()))
                .andExpect(jsonPath("$[0].foyer.nomFoyer").value(universite.getFoyer().getNomFoyer()));
    }

    @Test
    void testRetrieveUniversite() throws Exception {
        Mockito.when(universiteService.retrieveUniversite(anyLong())).thenReturn(universite);

        mockMvc.perform(get("/universite/retrieve-universite/{universite-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()))
                .andExpect(jsonPath("$.nomUniversite").value(universite.getNomUniversite()))
                .andExpect(jsonPath("$.adresse").value(universite.getAdresse()))
                .andExpect(jsonPath("$.foyer.idFoyer").value(universite.getFoyer().getIdFoyer()))
                .andExpect(jsonPath("$.foyer.nomFoyer").value(universite.getFoyer().getNomFoyer()));
    }

    @Test
    void testAddUniversite() throws Exception {
        Mockito.when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/universite/add-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()))
                .andExpect(jsonPath("$.nomUniversite").value(universite.getNomUniversite()))
                .andExpect(jsonPath("$.adresse").value(universite.getAdresse()))
                .andExpect(jsonPath("$.foyer.idFoyer").value(universite.getFoyer().getIdFoyer()))
                .andExpect(jsonPath("$.foyer.nomFoyer").value(universite.getFoyer().getNomFoyer()));
    }

    @Test
    void testModifyUniversite() throws Exception {
        Mockito.when(universiteService.modifyUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(put("/universite/modify-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()))
                .andExpect(jsonPath("$.nomUniversite").value(universite.getNomUniversite()))
                .andExpect(jsonPath("$.adresse").value(universite.getAdresse()))
                .andExpect(jsonPath("$.foyer.idFoyer").value(universite.getFoyer().getIdFoyer()))
                .andExpect(jsonPath("$.foyer.nomFoyer").value(universite.getFoyer().getNomFoyer()));
    }

    @Test
    void testRemoveUniversite() throws Exception {
        Mockito.doNothing().when(universiteService).removeUniversite(anyLong());

        mockMvc.perform(delete("/universite/remove-universite/{universite-id}", 1L))
                .andExpect(status().isOk());
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
                    .findAndRegisterModules() // To handle Java 8 Date/Time types if present
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
