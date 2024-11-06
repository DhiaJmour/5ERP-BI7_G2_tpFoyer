package tn.esprit.tpfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UniversiteServiceJUnitTest {

    @Autowired
    private UniversiteServiceImpl universiteService;

    @Autowired
    private UniversiteRepository universiteRepository;

    @Test
    @Order(1)
    void addUniversite() {
        Universite universite = new Universite();
        universite.setNomUniversite("Test University");
        universite.setAdresse("123 University St");

        Universite savedUniversite = universiteService.addUniversite(universite);
        assertNotNull(savedUniversite);
        assertEquals("Test University", savedUniversite.getNomUniversite());
        System.out.println("Add Universite: Ok");
    }

    @Test
    @Order(2)
    void retrieveAllUniversites() {
        List<Universite> universites = universiteService.retrieveAllUniversites();
        assertNotNull(universites);
        assertFalse(universites.isEmpty());
        System.out.println("Retrieve All Universites: Ok");
    }

    @Test
    @Order(3)
    void retrieveUniversite() {
        Universite universite = universiteService.retrieveUniversite(1L); // Adjust ID as necessary
        assertNotNull(universite);
        assertEquals("Test University", universite.getNomUniversite()); // Adjust based on your data
        System.out.println("Retrieve Universite: Ok");
    }

    @Test
    @Order(4)
    void removeUniversite() {
        universiteService.removeUniversite(1L); // Adjust ID as necessary
        assertThrows(EntityNotFoundException.class, () -> universiteService.retrieveUniversite(1L));
        System.out.println("Remove Universite: Ok");
    }

    @Test
    @Order(5)
    void modifyUniversite() {
        Universite universite = new Universite();
        universite.setIdUniversite(1L); // Adjust based on your data
        universite.setNomUniversite("Updated University");

        Universite updatedUniversite = universiteService.modifyUniversite(universite);
        assertNotNull(updatedUniversite);
        assertEquals("Updated University", updatedUniversite.getNomUniversite());
        System.out.println("Modify Universite: Ok");
    }
}
