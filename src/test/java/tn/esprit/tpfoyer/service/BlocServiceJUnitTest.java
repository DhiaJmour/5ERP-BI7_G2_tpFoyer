package tn.esprit.tpfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BlocServiceJUnitTest {

    @Autowired
    private BlocServiceImpl blocService;

    @Autowired
    private BlocRepository blocRepository;

    @Test
    @Order(1)
    void addBloc() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(50);

        Bloc savedBloc = blocService.addBloc(bloc);
        assertNotNull(savedBloc);
        assertEquals("Bloc A", savedBloc.getNomBloc());
        System.out.println("Add Bloc: Ok");
    }

    @Test
    @Order(2)
    void retrieveAllBlocs() {
        List<Bloc> blocs = blocService.retrieveAllBlocs();
        assertNotNull(blocs);
        assertFalse(blocs.isEmpty());
        System.out.println("Retrieve All Blocs: Ok");
    }

    @Test
    @Order(3)
    void retrieveBloc() {
        Bloc bloc = blocService.retrieveBloc(1L); // Adjust ID as necessary
        assertNotNull(bloc);
        assertEquals("Bloc A", bloc.getNomBloc()); // Adjust based on your data
        System.out.println("Retrieve Bloc: Ok");
    }

    @Test
    @Order(4)
    void removeBloc() {
        blocService.removeBloc(1L); // Adjust ID as necessary
        assertThrows(EntityNotFoundException.class, () -> blocService.retrieveBloc(1L));
        System.out.println("Remove Bloc: Ok");
    }

    @Test
    @Order(5)
    void retrieveBlocsSelonCapacite() {
        // Assume blocs are already added in previous tests
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(50);

        assertNotNull(result);
        assertEquals(0, result.size()); // Adjust the expected count based on your setup
        System.out.println("Retrieve Blocs Selon Capacite: Ok");
    }

    @Test
    @Order(6)
    void retrieveBlocsSelonCapacite_NoResults() {
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(300);
        assertNotNull(result);
        assertEquals(0, result.size()); // Expecting 0 blocs with capacity >= 300
        System.out.println("Retrieve Blocs Selon Capacite - No Results: Ok");
    }

    @Test
    @Order(7)
    void trouverBlocsSansFoyer() {
        // Arrange
        Bloc bloc1 = new Bloc();
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(50);
        blocRepository.save(bloc1); // Persist the bloc to the repository

        Bloc bloc2 = new Bloc();
        bloc2.setNomBloc("Bloc B");
        bloc2.setCapaciteBloc(100);
        blocRepository.save(bloc2); // Persist another bloc

        // Act
        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); // Expecting both blocs to be without foyer
        System.out.println("Trouver Blocs Sans Foyer: Ok");
    }

    @Test
    @Order(8)
    void trouverBlocsParNomEtCap() {
        // Arrange
        Bloc bloc1 = new Bloc();
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(100);
        blocRepository.save(bloc1);

        Bloc bloc2 = new Bloc();
        bloc2.setNomBloc("Bloc B");
        bloc2.setCapaciteBloc(200);
        blocRepository.save(bloc2);

        // Act
        List<Bloc> result = blocService.trouverBlocsParNomEtCap("Bloc A", 100);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size()); // Expecting one bloc matching the criteria
        assertEquals("Bloc A", result.get(0).getNomBloc());
        System.out.println("Trouver Blocs Par Nom Et Cap: Ok");
    }
}
