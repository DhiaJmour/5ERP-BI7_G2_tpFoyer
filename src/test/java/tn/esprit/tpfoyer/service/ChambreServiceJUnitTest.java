package tn.esprit.tpfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChambreServiceJUnitTest {

    @Autowired
    private ChambreServiceImpl chambreService;

    @Autowired
    private ChambreRepository chambreRepository;

    @Test
    @Order(1)
    void addChambre() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE); // Assuming SIMPLE is a valid enum value

        Chambre savedChambre = chambreService.addChambre(chambre);
        assertNotNull(savedChambre);
        assertEquals(101, savedChambre.getNumeroChambre());
        System.out.println("Add Chambre: Ok");
    }

    @Test
    @Order(2)
    void retrieveAllChambres() {
        List<Chambre> chambres = chambreService.retrieveAllChambres();
        assertNotNull(chambres);
        assertFalse(chambres.isEmpty());
        System.out.println("Retrieve All Chambres: Ok");
    }

    @Test
    @Order(3)
    void retrieveChambre() {
        Chambre chambre = chambreService.retrieveChambre(1L); // Adjust ID as necessary
        assertNotNull(chambre);
        assertEquals(101, chambre.getNumeroChambre()); // Adjust based on your data
        System.out.println("Retrieve Chambre: Ok");
    }

    @Test
    @Order(4)
    void removeChambre() {
        chambreService.removeChambre(1L); // Adjust ID as necessary
        assertThrows(EntityNotFoundException.class, () -> chambreService.retrieveChambre(1L));
        System.out.println("Remove Chambre: Ok");
    }

    @Test
    @Order(5)
    void recupererChambresSelonTyp() {
        List<Chambre> chambres = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);
        assertNotNull(chambres);
        assertTrue(chambres.isEmpty());
        System.out.println("Retrieve Chambres by Type: Ok");
    }


}
