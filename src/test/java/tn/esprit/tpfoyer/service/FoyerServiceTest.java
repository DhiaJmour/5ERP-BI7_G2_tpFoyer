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
}
