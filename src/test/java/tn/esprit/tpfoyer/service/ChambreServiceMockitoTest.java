package tn.esprit.tpfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ChambreServiceMockitoTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addChambre() {
        // Arrange
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE); // Assuming SIMPLE is a valid enum value
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Act
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Assert
        assertNotNull(savedChambre);
        assertEquals(101, savedChambre.getNumeroChambre());
        verify(chambreRepository, times(1)).save(argThat(ch -> ch.getNumeroChambre() == 101));
    }

    @Test
    void retrieveAllChambres() {
        // Arrange
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(new Chambre());
        chambres.add(new Chambre());
        when(chambreRepository.findAll()).thenReturn(chambres);

        // Act
        List<Chambre> retrievedChambres = chambreService.retrieveAllChambres();

        // Assert
        assertNotNull(retrievedChambres);
        assertEquals(2, retrievedChambres.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void retrieveChambre() {
        // Arrange
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Act
        Chambre retrievedChambre = chambreService.retrieveChambre(1L);

        // Assert
        assertNotNull(retrievedChambre);
        assertEquals(1L, retrievedChambre.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void retrieveChambre_NotFound() {
        // Arrange
        when(chambreRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chambreService.retrieveChambre(1L));
    }

    @Test
    void modifyChambre() {
        // Arrange
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(102);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Act
        Chambre updatedChambre = chambreService.modifyChambre(chambre);

        // Assert
        assertNotNull(updatedChambre);
        assertEquals(102, updatedChambre.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void modifyChambre_NotFound() {
        // Arrange
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(102);

        when(chambreRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chambreService.modifyChambre(chambre));
    }

    @Test
    void removeChambre() {
        // Act
        chambreService.removeChambre(1L);

        // Assert
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void removeChambre_NotFound() {
        // Arrange
        doThrow(new EntityNotFoundException("Chambre not found")).when(chambreRepository).deleteById(1L);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chambreService.removeChambre(1L));
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void recupererChambresSelonTyp() {
        // Arrange
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(new Chambre());
        chambres.add(new Chambre());
        when(chambreRepository.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(chambres);

        // Act
        List<Chambre> retrievedChambres = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        // Assert
        assertNotNull(retrievedChambres);
        assertEquals(2, retrievedChambres.size());
        verify(chambreRepository, times(1)).findAllByTypeC(TypeChambre.SIMPLE);
    }

    @Test
    void recupererChambresSelonTyp_EmptyList() {
        // Arrange
        when(chambreRepository.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(new ArrayList<>());

        // Act
        List<Chambre> retrievedChambres = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        // Assert
        assertNotNull(retrievedChambres);
        assertTrue(retrievedChambres.isEmpty());
    }

    @Test
    void trouverChambreSelonEtudiant() {
        // Arrange
        long cin = 123456; // Example CIN
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE);

        when(chambreRepository.trouverChselonEt(cin)).thenReturn(chambre);

        // Act
        Chambre retrievedChambre = chambreService.trouverchambreSelonEtudiant(cin);

        // Assert
        assertNotNull(retrievedChambre);
        assertEquals(101, retrievedChambre.getNumeroChambre());
        verify(chambreRepository, times(1)).trouverChselonEt(cin);
    }
}
