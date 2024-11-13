package tn.esprit.tpfoyer;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UniversiteServiceMockitoTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUniversite() {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("Université de Test");
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        Universite savedUniversite = universiteService.addUniversite(universite);

        // Assert
        assertNotNull(savedUniversite);
        assertEquals("Université de Test", savedUniversite.getNomUniversite());
        verify(universiteRepository, times(1)).save(argThat(u -> "Université de Test".equals(u.getNomUniversite())));
    }

    @Test
    void retrieveAllUniversites() {
        // Arrange
        List<Universite> universites = new ArrayList<>();
        universites.add(new Universite());
        universites.add(new Universite());
        when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> retrievedUniversites = universiteService.retrieveAllUniversites();

        // Assert
        assertNotNull(retrievedUniversites);
        assertEquals(2, retrievedUniversites.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void retrieveUniversite() {
        // Arrange
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act
        Universite retrievedUniversite = universiteService.retrieveUniversite(1L);

        // Assert
        assertNotNull(retrievedUniversite);
        assertEquals(1L, retrievedUniversite.getIdUniversite());
        verify(universiteRepository, times(1)).findById(1L);
    }

     /*@Test
   void retrieveUniversite_NotFound() {
        // Arrange
        when(universiteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> universiteService.retrieveUniversite(1L));
    }*/

    @Test
    void modifyUniversite() {
        // Arrange
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Université Modifiée");
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        Universite updatedUniversite = universiteService.modifyUniversite(universite);

        // Assert
        assertNotNull(updatedUniversite);
        assertEquals("Université Modifiée", updatedUniversite.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void removeUniversite() {
        // Act
        universiteService.removeUniversite(1L);

        // Assert
        verify(universiteRepository, times(1)).deleteById(1L);
    }

    @Test
    void removeUniversite_NotFound() {
        // Arrange
        doThrow(new EntityNotFoundException("Université not found")).when(universiteRepository).deleteById(1L);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> universiteService.removeUniversite(1L));
        verify(universiteRepository, times(1)).deleteById(1L);
    }
}
