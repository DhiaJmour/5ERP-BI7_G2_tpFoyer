package tn.esprit.tpfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EtudiantServiceMockitoTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("John");
        etudiant.setPrenomEtudiant("Doe");
        etudiant.setCinEtudiant(123456);
        etudiant.setDateNaissance(new Date());

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        // Assert
        assertNotNull(savedEtudiant);
        assertEquals("John", savedEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void retrieveAllEtudiants() {
        // Arrange
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant());
        etudiants.add(new Etudiant());
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        // Act
        List<Etudiant> retrievedEtudiants = etudiantService.retrieveAllEtudiants();

        // Assert
        assertNotNull(retrievedEtudiants);
        assertEquals(2, retrievedEtudiants.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void retrieveEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(1L);

        // Assert
        assertNotNull(retrievedEtudiant);
        assertEquals(1L, retrievedEtudiant.getIdEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void retrieveEtudiant_NotFound() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> etudiantService.retrieveEtudiant(1L));
    }

    @Test
    void modifyEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Jane");
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant updatedEtudiant = etudiantService.modifyEtudiant(etudiant);

        // Assert
        assertNotNull(updatedEtudiant);
        assertEquals("Jane", updatedEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void removeEtudiant() {
        // Act
        etudiantService.removeEtudiant(1L);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void recupererEtudiantParCin() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setCinEtudiant(123456);
        when(etudiantRepository.findEtudiantByCinEtudiant(123456)).thenReturn(etudiant);

        // Act
        Etudiant retrievedEtudiant = etudiantService.recupererEtudiantParCin(123456);

        // Assert
        assertNotNull(retrievedEtudiant);
        assertEquals(123456, retrievedEtudiant.getCinEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(123456);
    }
}
