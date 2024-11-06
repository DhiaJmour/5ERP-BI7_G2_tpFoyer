package tn.esprit.tpfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BlocServiceMockitoTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBloc() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(50);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc savedBloc = blocService.addBloc(bloc);

        // Assert
        assertNotNull(savedBloc);
        assertEquals("Bloc A", savedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void retrieveAllBlocs() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc());
        blocs.add(new Bloc());
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> retrievedBlocs = blocService.retrieveAllBlocs();

        // Assert
        assertNotNull(retrievedBlocs);
        assertEquals(2, retrievedBlocs.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void retrieveBloc() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        // Assert
        assertNotNull(retrievedBloc);
        assertEquals(1L, retrievedBloc.getIdBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void retrieveBloc_NotFound() {
        // Arrange
        when(blocRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> blocService.retrieveBloc(1L));
    }

    @Test
    void modifyBloc() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(50);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc updatedBloc = blocService.modifyBloc(bloc);

        // Assert
        assertNotNull(updatedBloc);
        assertEquals("Bloc A", updatedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void removeBloc() {
        // Act
        blocService.removeBloc(1L);

        // Assert
        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void retrieveBlocsSelonCapacite() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();

        // Use the default constructor and setters to set properties
        Bloc blocA = new Bloc();
        blocA.setIdBloc(1L);
        blocA.setNomBloc("Bloc A");
        blocA.setCapaciteBloc(100L); // Ensure this is a long

        Bloc blocB = new Bloc();
        blocB.setIdBloc(2L);
        blocB.setNomBloc("Bloc B");
        blocB.setCapaciteBloc(50L);

        Bloc blocC = new Bloc();
        blocC.setIdBloc(3L);
        blocC.setNomBloc("Bloc C");
        blocC.setCapaciteBloc(200L);

        // Add blocs to the list
        blocs.add(blocA);
        blocs.add(blocB);
        blocs.add(blocC);

        // Mocking the repository
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(100L); // Pass a long

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); // Expecting 2 blocs with capacity >= 100
    }


    @Test
    void testTrouverBlocsSansFoyer() {
      Bloc  bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(100);

     Bloc   bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc B");
        bloc2.setCapaciteBloc(200);
        // Arrange
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAllByFoyerIsNull(); // Verify method was called
    }

    @Test
    void testTrouverBlocsParNomEtCap() {
     Bloc bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(100);

     Bloc bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc B");
        bloc2.setCapaciteBloc(200);
        // Arrange
        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc A", 100)).thenReturn(Arrays.asList(bloc1));

        // Act
        List<Bloc> result = blocService.trouverBlocsParNomEtCap("Bloc A", 100);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc("Bloc A", 100); // Verify method was called
    }


}
