package tn.esprit.tpfoyer.control;

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
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IChambreService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChambreRestController.class)
class ChambreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IChambreService chambreService;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        //init
        // Initialize mock Chambre object
        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setReservations(new HashSet<>());
        // Assuming Bloc is another entity; set it to null or a mock if necessary
        chambre.setBloc(null);
    }

    @Test
    void testGetChambres() throws Exception {
        List<Chambre> chambres = Arrays.asList(chambre);
        Mockito.when(chambreService.retrieveAllChambres()).thenReturn(chambres);

        mockMvc.perform(get("/chambre/retrieve-all-chambres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idChambre").value(chambre.getIdChambre()))
                .andExpect(jsonPath("$[0].numeroChambre").value(chambre.getNumeroChambre()))
                .andExpect(jsonPath("$[0].typeC").value(chambre.getTypeC().toString()));
    }

    @Test
    void testRetrieveChambre() throws Exception {
        Mockito.when(chambreService.retrieveChambre(anyLong())).thenReturn(chambre);

        mockMvc.perform(get("/chambre/retrieve-chambre/{chambre-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idChambre").value(chambre.getIdChambre()))
                .andExpect(jsonPath("$.numeroChambre").value(chambre.getNumeroChambre()))
                .andExpect(jsonPath("$.typeC").value(chambre.getTypeC().toString()));
    }

    @Test
    void testAddChambre() throws Exception {
        Mockito.when(chambreService.addChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(post("/chambre/add-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(chambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(chambre.getIdChambre()))
                .andExpect(jsonPath("$.numeroChambre").value(chambre.getNumeroChambre()))
                .andExpect(jsonPath("$.typeC").value(chambre.getTypeC().toString()));
    }

    @Test
    void testModifyChambre() throws Exception {
        Mockito.when(chambreService.modifyChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(put("/chambre/modify-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(chambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(chambre.getIdChambre()))
                .andExpect(jsonPath("$.numeroChambre").value(chambre.getNumeroChambre()))
                .andExpect(jsonPath("$.typeC").value(chambre.getTypeC().toString()));
    }

    @Test
    void testRemoveChambre() throws Exception {
        Mockito.doNothing().when(chambreService).removeChambre(anyLong());

        mockMvc.perform(delete("/chambre/remove-chambre/{chambre-id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testTrouverChambresSelonTyp() throws Exception {
        List<Chambre> chambres = Arrays.asList(chambre);
        Mockito.when(chambreService.recupererChambresSelonTyp(eq(TypeChambre.SIMPLE))).thenReturn(chambres);

        mockMvc.perform(get("/chambre/trouver-chambres-selon-typ/{tc}", TypeChambre.SIMPLE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idChambre").value(chambre.getIdChambre()))
                .andExpect(jsonPath("$[0].numeroChambre").value(chambre.getNumeroChambre()))
                .andExpect(jsonPath("$[0].typeC").value(chambre.getTypeC().toString()));
    }

    @Test
    void testTrouverChambreSelonEtudiant() throws Exception {
        Mockito.when(chambreService.trouverchambreSelonEtudiant(anyLong())).thenReturn(chambre);

        mockMvc.perform(get("/chambre/trouver-chambre-selon-etudiant/{cin}", 12345678L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idChambre").value(chambre.getIdChambre()))
                .andExpect(jsonPath("$.numeroChambre").value(chambre.getNumeroChambre()))
                .andExpect(jsonPath("$.typeC").value(chambre.getTypeC().toString()));
    }
    @Test
void testGetOccupationParType() throws Exception {
    long occupationCount = 5;
    Mockito.when(chambreService.calculerOccupationParType(eq(TypeChambre.SIMPLE))).thenReturn(occupationCount);

    mockMvc.perform(get("/chambre/occupation-par-type/{type}", TypeChambre.SIMPLE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(String.valueOf(occupationCount)));
}

@Test
void testFiltrerChambres() throws Exception {
    List<Chambre> chambres = Arrays.asList(chambre);
    double prixMax = 500.0;
    double tailleMin = 15.0;
    Mockito.when(chambreService.filtrerChambres(prixMax, tailleMin)).thenReturn(chambres);

    mockMvc.perform(get("/chambre/filtrer")
                    .param("prixMax", String.valueOf(prixMax))
                    .param("tailleMin", String.valueOf(tailleMin)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].idChambre").value(chambre.getIdChambre()))
            .andExpect(jsonPath("$[0].numeroChambre").value(chambre.getNumeroChambre()))
            .andExpect(jsonPath("$[0].typeC").value(chambre.getTypeC().toString()));
}
/*@Test
void testRetrieveChambreNotFound() throws Exception {
    Mockito.when(chambreService.retrieveChambre(anyLong()))
            .thenThrow(new EntityNotFoundException("Chambre not found"));

    mockMvc.perform(get("/chambre/retrieve-chambre/{chambre-id}", 999L))
            .andExpect(status().isNotFound());
}*/


    /**
     * Utility method to convert Java objects to JSON string.
     *
     * @param obj the object to convert
     * @return JSON string representation of the object
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper()
                    .findAndRegisterModules() // To handle any additional modules if needed
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
