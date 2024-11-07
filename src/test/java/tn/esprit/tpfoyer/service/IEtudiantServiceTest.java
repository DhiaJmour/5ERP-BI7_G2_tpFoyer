package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class IEtudiantServiceTest {
    EtudiantServiceImpl etudiantService;
    @Mock
    EtudiantRepository etudiantRepository;
    @BeforeEach
    void setUp(){
        this.etudiantService = new EtudiantServiceImpl(etudiantRepository);
    }
    @Test
    void retrieveAllEtudiants() {
        Etudiant etudiant1 = new Etudiant("John", "Doe", 123456789L, new Date());
        Etudiant etudiant2 = new Etudiant("Jane", "Doe", 987654321L, new Date());
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant1, etudiant2));

        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();

        assertEquals(2, etudiants.size());
        assertEquals("John", etudiants.get(0).getNomEtudiant());
        verify(etudiantRepository, times(1)).findAll();
    }
    @Test
    void retrieveEtudiant() {
        Long id = 1L;
        Etudiant mockEtudiant = new Etudiant("John", "Doe", 123456789L, new Date());
        when(etudiantRepository.findById(id)).thenReturn(Optional.of(mockEtudiant));

        Etudiant etudiant = etudiantService.retrieveEtudiant(id);

        assertNotNull(etudiant);
        assertEquals("John", etudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(id);
    }
    @Test
    void addEtudiant() {
        Etudiant newEtudiant = new Etudiant("Jane", "Doe", 987654321L, new Date());
        when(etudiantRepository.save(newEtudiant)).thenReturn(newEtudiant);

        Etudiant savedEtudiant = etudiantService.addEtudiant(newEtudiant);

        assertEquals("Jane", savedEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(newEtudiant);
    }
    @Test
    void modifyEtudiant() {
        Etudiant existingEtudiant = new Etudiant("John", "Doe", 123456789L, new Date());
        when(etudiantRepository.save(existingEtudiant)).thenReturn(existingEtudiant);

        Etudiant modifiedEtudiant = etudiantService.modifyEtudiant(existingEtudiant);

        assertEquals("John", modifiedEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(existingEtudiant);
    }
    @Test
    void removeEtudiant() {
        Long idToRemove = 1L;

        etudiantService.removeEtudiant(idToRemove);

        verify(etudiantRepository, times(1)).deleteById(idToRemove);
    }
    @Test
    void recupererEtudiantParCin() {
        long cin = 123456789L;
        Etudiant mockEtudiant = new Etudiant("John", "Doe", cin, new Date());
        when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(mockEtudiant);

        Etudiant etudiant = etudiantService.recupererEtudiantParCin(cin);

        assertNotNull(etudiant);
        assertEquals("John", etudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
    }

}