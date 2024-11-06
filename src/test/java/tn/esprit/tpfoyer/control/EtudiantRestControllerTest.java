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
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EtudiantRestController.class)
class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantService;

    private Etudiant etudiant;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp() throws Exception {
        // Initialize mock Etudiant object
        Date dateNaissance = dateFormat.parse("1995-08-20");
        etudiant = new Etudiant(1L, "Doe", "John", 12345678L, dateNaissance, null);
    }

    @Test
    void testGetEtudiants() throws Exception {
        List<Etudiant> etudiants = Arrays.asList(etudiant);
        Mockito.when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiants);

        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idEtudiant").value(etudiant.getIdEtudiant()))
                .andExpect(jsonPath("$[0].nomEtudiant").value(etudiant.getNomEtudiant()))
                .andExpect(jsonPath("$[0].prenomEtudiant").value(etudiant.getPrenomEtudiant()))
                .andExpect(jsonPath("$[0].cinEtudiant").value(etudiant.getCinEtudiant()));
    }

    @Test
    void testRetrieveEtudiantParCin() throws Exception {
        Mockito.when(etudiantService.recupererEtudiantParCin(anyLong())).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/{cin}", etudiant.getCinEtudiant()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idEtudiant").value(etudiant.getIdEtudiant()))
                .andExpect(jsonPath("$.nomEtudiant").value(etudiant.getNomEtudiant()))
                .andExpect(jsonPath("$.prenomEtudiant").value(etudiant.getPrenomEtudiant()))
                .andExpect(jsonPath("$.cinEtudiant").value(etudiant.getCinEtudiant()));
    }

    @Test
    void testRetrieveEtudiant() throws Exception {
        Mockito.when(etudiantService.retrieveEtudiant(anyLong())).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant/{etudiant-id}", etudiant.getIdEtudiant()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idEtudiant").value(etudiant.getIdEtudiant()))
                .andExpect(jsonPath("$.nomEtudiant").value(etudiant.getNomEtudiant()))
                .andExpect(jsonPath("$.prenomEtudiant").value(etudiant.getPrenomEtudiant()))
                .andExpect(jsonPath("$.cinEtudiant").value(etudiant.getCinEtudiant()));
    }

    @Test
    void testAddEtudiant() throws Exception {
        Mockito.when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(etudiant.getIdEtudiant()))
                .andExpect(jsonPath("$.nomEtudiant").value(etudiant.getNomEtudiant()))
                .andExpect(jsonPath("$.prenomEtudiant").value(etudiant.getPrenomEtudiant()))
                .andExpect(jsonPath("$.cinEtudiant").value(etudiant.getCinEtudiant()));
    }

    @Test
    void testModifyEtudiant() throws Exception {
        Mockito.when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(etudiant.getIdEtudiant()))
                .andExpect(jsonPath("$.nomEtudiant").value(etudiant.getNomEtudiant()))
                .andExpect(jsonPath("$.prenomEtudiant").value(etudiant.getPrenomEtudiant()))
                .andExpect(jsonPath("$.cinEtudiant").value(etudiant.getCinEtudiant()));
    }

    @Test
    void testRemoveEtudiant() throws Exception {
        Mockito.doNothing().when(etudiantService).removeEtudiant(anyLong());

        mockMvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", etudiant.getIdEtudiant()))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper()
                    .findAndRegisterModules() // To handle Java 8 Date/Time types
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
