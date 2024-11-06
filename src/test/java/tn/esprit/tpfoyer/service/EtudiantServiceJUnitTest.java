package tn.esprit.tpfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EtudiantServiceJUnitTest {

    @Autowired
    private EtudiantServiceImpl etudiantService;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Test
    @Order(1)
    void addEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Alice");
        etudiant.setPrenomEtudiant("Smith");
        etudiant.setCinEtudiant(654321);
        etudiant.setDateNaissance(new Date());

        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);
        assertNotNull(savedEtudiant);
        assertEquals("Alice", savedEtudiant.getNomEtudiant());
        System.out.println("Add Etudiant: Ok");
    }
    @Test
    @Order(2)
    void recupererEtudiantParCin() {
        Etudiant etudiant = etudiantService.recupererEtudiantParCin(654321); // Adjust CIN as necessary
        assertNotNull(etudiant);
        assertEquals(654321, etudiant.getCinEtudiant());
        System.out.println("Recuperer Etudiant Par CIN: Ok");
    }

    @Test
    @Order(3)
    void retrieveAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();
        assertNotNull(etudiants);
        assertFalse(etudiants.isEmpty());
        System.out.println("Retrieve All Etudiants: Ok");
    }

    @Test
    @Order(4)
    void retrieveEtudiant() {
        Etudiant etudiant = etudiantService.retrieveEtudiant(1L); // Adjust ID as necessary
        assertNotNull(etudiant);
        assertEquals("Alice", etudiant.getNomEtudiant()); // Adjust based on your data
        System.out.println("Retrieve Etudiant: Ok");
    }

    @Test
    @Order(5)
    void removeEtudiant() {
        etudiantService.removeEtudiant(1L); // Adjust ID as necessary
        assertThrows(EntityNotFoundException.class, () -> etudiantService.retrieveEtudiant(1L));
        System.out.println("Remove Etudiant: Ok");
    }

    @Test
    @Order(6)
    void modifyEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L); // Adjust based on your data
        etudiant.setNomEtudiant("Updated Name");

        Etudiant updatedEtudiant = etudiantService.modifyEtudiant(etudiant);
        assertNotNull(updatedEtudiant);
        assertEquals("Updated Name", updatedEtudiant.getNomEtudiant());
        System.out.println("Modify Etudiant: Ok");
    }


}
