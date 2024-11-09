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
        chambre.setTypeC(TypeChambre.SIMPLE);

        Chambre savedChambre = chambreService.addChambre(chambre);
        assertNotNull(savedChambre);
        assertEquals(101, savedChambre.getNumeroChambre());

        // Vérifier que la chambre a bien été enregistrée en base de données
        Chambre foundChambre = chambreRepository.findById(savedChambre.getIdChambre()).orElse(null);
        assertNotNull(foundChambre);
        assertEquals(101, foundChambre.getNumeroChambre());

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
         // On suppose que l'ID 1 existe dans la base
         chambreService.removeChambre(1L); 

         // Vérifier qu'une exception est lancée quand on tente de récupérer une chambre supprimée
        assertThrows(EntityNotFoundException.class, () -> chambreService.retrieveChambre(1L));

        System.out.println("Remove Chambre: Ok");
}

    @Test
    @Order(5)
    void recupererChambresSelonTyp() {
         // Créer une chambre de type SIMPLE avant de tester
         Chambre chambre = new Chambre();
         chambre.setNumeroChambre(102);
         chambre.setTypeC(TypeChambre.SIMPLE);
         chambreService.addChambre(chambre);

         // Tester la récupération des chambres de type SIMPLE
        List<Chambre> chambres = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);
        assertNotNull(chambres);
        assertFalse(chambres.isEmpty()); // S'assurer qu'il y a des chambres de type SIMPLE
        assertEquals(TypeChambre.SIMPLE, chambres.get(0).getTypeC()); // Vérifier que le type est SIMPLE

    System.out.println("Retrieve Chambres by Type: Ok");
}
   @Test
   @Order(6)
   void modifyChambre() {
        // Créer une chambre de type SIMPLE
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(103);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambreService.addChambre(chambre);

       // Modifier la chambre
        chambre.setNumeroChambre(104);
        Chambre modifiedChambre = chambreService.modifyChambre(chambre);
        assertNotNull(modifiedChambre);
        assertEquals(104, modifiedChambre.getNumeroChambre()); // Vérifier la modification

      System.out.println("Modify Chambre: Ok");
}

}
