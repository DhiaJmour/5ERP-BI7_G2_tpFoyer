package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(FoyerRestController.class)
class FoyerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFoyerService foyerService;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        // Initialize mock Foyer object
        foyer = new Foyer(1L, "Foyer A", 200, null, null);
    }

    @Test
    void testGetFoyers() throws Exception {
        List<Foyer> foyers = Arrays.asList(foyer);
        Mockito.when(foyerService.retrieveAllFoyers()).thenReturn(foyers);

        mockMvc.perform(get("/foyer/retrieve-all-foyers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idFoyer").value(foyer.getIdFoyer()))
                .andExpect(jsonPath("$[0].nomFoyer").value(foyer.getNomFoyer()));
    }

    @Test
    void testRetrieveFoyer() throws Exception {
        Mockito.when(foyerService.retrieveFoyer(anyLong())).thenReturn(foyer);

        mockMvc.perform(get("/foyer/retrieve-foyer/{foyer-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idFoyer").value(foyer.getIdFoyer()))
                .andExpect(jsonPath("$.nomFoyer").value(foyer.getNomFoyer()));
    }

    @Test
    void testAddFoyer() throws Exception {
        Mockito.when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(post("/foyer/add-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(foyer.getIdFoyer()))
                .andExpect(jsonPath("$.nomFoyer").value(foyer.getNomFoyer()));
    }

    @Test
    void testModifyFoyer() throws Exception {
        Mockito.when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(put("/foyer/modify-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(foyer.getIdFoyer()))
                .andExpect(jsonPath("$.nomFoyer").value(foyer.getNomFoyer()));
    }

    @Test
    void testRemoveFoyer() throws Exception {
        Mockito.doNothing().when(foyerService).removeFoyer(anyLong());

        mockMvc.perform(delete("/foyer/remove-foyer/{foyer-id}", 1L))
                .andExpect(status().isOk());
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
