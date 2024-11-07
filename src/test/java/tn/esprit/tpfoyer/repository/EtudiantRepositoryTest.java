package tn.esprit.tpfoyer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer.entity.Etudiant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EtudiantRepositoryTest {

    @Autowired
    private EtudiantRepository etudiantRepository;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateNaissance = formatter.parse("20-03-2000");

        etudiant = new Etudiant("alice", "doe", 142021452L, dateNaissance);
        etudiant = etudiantRepository.save(etudiant);
    }

    @Test
    void findEtudiantByCinEtudiant_ShouldReturnEtudiant_WhenCinExists() {

        Etudiant foundEtudiant = etudiantRepository.findEtudiantByCinEtudiant(142021452L);


        assertThat(foundEtudiant).isNotNull();
        assertThat(foundEtudiant.getCinEtudiant()).isEqualTo(etudiant.getCinEtudiant());
        assertThat(foundEtudiant.getNomEtudiant()).isEqualTo(etudiant.getNomEtudiant());
        assertThat(foundEtudiant.getPrenomEtudiant()).isEqualTo(etudiant.getPrenomEtudiant());
        assertThat(foundEtudiant.getDateNaissance()).isEqualTo(etudiant.getDateNaissance());
    }

    @Test
    void findEtudiantByCinEtudiant_ShouldReturnNull_WhenCinDoesNotExist() {

        Etudiant foundEtudiant = etudiantRepository.findEtudiantByCinEtudiant(123456789L);


        assertThat(foundEtudiant).isNull();
    }
}
