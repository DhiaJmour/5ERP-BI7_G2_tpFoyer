package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
@WebMvcTest(controllers = EtudiantRestController.class)
class EtudiantRestControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    IEtudiantService etudiantService;

    @Test
    void getEtudiantsShouldReturnvariablesAllAtOnce() throws Exception{
        List<Etudiant> mockEtudiants = Arrays.asList(
                new Etudiant("John","Doe",123456789L,new Date()),
                new Etudiant("Jane","Doe",987654321L,new Date())
                );
        when(etudiantService.retrieveAllEtudiants()).thenReturn(mockEtudiants);

        mvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEtudiant").value(0))
                .andExpect(jsonPath("$[0].nomEtudiant").value("John"))
                .andExpect(jsonPath("$[0].prenomEtudiant").value("Doe"))
                .andExpect(jsonPath("$[0].cinEtudiant").value(123456789L))
                .andExpect(jsonPath("$[1].idEtudiant").value(0))
                .andExpect(jsonPath("$[1].nomEtudiant").value("Jane"))
                .andExpect(jsonPath("$[1].prenomEtudiant").value("Doe"))
                .andExpect(jsonPath("$[1].cinEtudiant").value(987654321L));
        verify(etudiantService, times(1)).retrieveAllEtudiants();
    }

    @Test
    void retrieveEtudiantParCin() throws Exception {
        Long cin = 123456789L;
        Etudiant mockEtudiant = new Etudiant("jhon","doe",cin, new Date());
        when(etudiantService.recupererEtudiantParCin(cin)).thenReturn(mockEtudiant);
        mvc.perform(get("/etudiant/retrieve-etudiant-cin/{cin}",cin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("jhon"))
                .andExpect(jsonPath("$.prenomEtudiant").value("doe"))
                .andExpect(jsonPath("$.cinEtudiant").value(cin));
    }

    @Test
    void retrieveEtudiant() throws Exception {
        Long id = 1L;
        Etudiant mockEtudiant = new Etudiant("Jane", "Doe", 987654321L, new Date());
        when(etudiantService.retrieveEtudiant(id)).thenReturn(mockEtudiant);

        mvc.perform(get("/etudiant/retrieve-etudiant/{etudiant-id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("Jane"))
                .andExpect(jsonPath("$.prenomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.cinEtudiant").value(987654321L));
    }

    @Test
    void addEtudiant() throws Exception {
        Etudiant newEtudiant = new Etudiant("John", "Doe", 123456789L, new Date());
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(newEtudiant);

        mvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomEtudiant\":\"John\",\"prenomEtudiant\":\"Doe\",\"cinEtudiant\":123456789,\"dateNaissance\":\"2024-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"))
                .andExpect(jsonPath("$.prenomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.cinEtudiant").value(123456789L));
    }

    @Test
    void removeEtudiant() throws Exception {
        Long idToRemove = 1L;

        mvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", idToRemove))
                .andExpect(status().isOk());

        verify(etudiantService, times(1)).removeEtudiant(idToRemove);
    }

    @Test
    void modifyEtudiant() throws Exception {
        Etudiant updatedEtudiant = new Etudiant("Jane", "Doe", 987654321L, new Date());
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(updatedEtudiant);

        mvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomEtudiant\":\"Jane\",\"prenomEtudiant\":\"Doe\",\"cinEtudiant\":987654321,\"dateNaissance\":\"2024-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("Jane"))
                .andExpect(jsonPath("$.prenomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.cinEtudiant").value(987654321L));
    }
}