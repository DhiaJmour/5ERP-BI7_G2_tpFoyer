package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class FoyerServiceTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @Test
    public void testAddFoyer() {
        // Création de l'objet Foyer avec le constructeur personnalisé
        Foyer foyer = new Foyer("Test Foyer", 100);

        // Simulation du comportement du repository
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Appel de la méthode à tester
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Vérification du résultat
        assertThat(savedFoyer).isNotNull();
        assertThat(savedFoyer.getNomFoyer()).isEqualTo("Test Foyer");
        assertThat(savedFoyer.getCapaciteFoyer()).isEqualTo(100);
    }
    @Test
    public void testModifyFoyer() {
        // Préparation des données de test
        Foyer foyer = new Foyer("Foyer Initial", 100);
        foyer.setIdFoyer(1L);

        Foyer modifiedFoyer = new Foyer("Foyer Modifié", 120);
        modifiedFoyer.setIdFoyer(1L);

        // Simulation du comportement du repository
        when(foyerRepository.save(foyer)).thenReturn(modifiedFoyer);

        // Appel de la méthode à tester
        Foyer result = foyerService.modifyFoyer(foyer);

        // Vérification des résultats
        assertThat(result).isNotNull();
        assertThat(result.getNomFoyer()).isEqualTo("Foyer Modifié");
        assertThat(result.getCapaciteFoyer()).isEqualTo(120);

        // Vérification que la méthode du repository a été appelée
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testRemoveFoyer() {
        // Appel de la méthode à tester
        foyerService.removeFoyer(1L);

        // Vérification que la méthode du repository a été appelée
        verify(foyerRepository, times(1)).deleteById(1L);
    }

}
