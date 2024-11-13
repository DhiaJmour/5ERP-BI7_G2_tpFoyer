package tn.esprit.tpfoyer;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

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
        universite.setNomUniversite("Université de Test");

        Universite savedUniversite = universiteService.addUniversite(universite);
        assertNotNull(savedUniversite);
        assertEquals("Université de Test", savedUniversite.getNomUniversite());

        // Vérifier que l'université a bien été enregistrée en base de données
        Universite foundUniversite = universiteRepository.findById(savedUniversite.getIdUniversite()).orElse(null);
        assertNotNull(foundUniversite);
        assertEquals("Université de Test", foundUniversite.getNomUniversite());

        System.out.println("Add Université: Ok");
    }

    @Test
    @Order(2)
    void retrieveAllUniversites() {
        List<Universite> universites = universiteService.retrieveAllUniversites();
        assertNotNull(universites);
        assertFalse(universites.isEmpty());
        System.out.println("Retrieve All Universités: Ok");
    }

    @Test
    @Order(3)
    void retrieveUniversite() {
        Universite universite = universiteService.retrieveUniversite(1L); // Ajuster l'ID si nécessaire
        assertNotNull(universite);
        assertEquals("Université de Test", universite.getNomUniversite()); // Adapter en fonction de vos données
        System.out.println("Retrieve Université: Ok");
    }

    @Test
    @Order(4)
    void removeUniversite() {
        // On suppose que l'ID 1 existe dans la base
        universiteService.removeUniversite(1L);

        // Vérifier qu'une exception est lancée quand on tente de récupérer une université supprimée
        assertThrows(EntityNotFoundException.class, () -> universiteService.retrieveUniversite(1L));

        System.out.println("Remove Université: Ok");
    }

    @Test
    @Order(5)
    void modifyUniversite() {
        // Créer une université
        Universite universite = new Universite();
        universite.setNomUniversite("Université à Modifier");
        universiteService.addUniversite(universite);

        // Modifier l'université
        universite.setNomUniversite("Université Modifiée");
        Universite modifiedUniversite = universiteService.modifyUniversite(universite);
        assertNotNull(modifiedUniversite);
        assertEquals("Université Modifiée", modifiedUniversite.getNomUniversite());

        System.out.println("Modify Université: Ok");
    }
}
