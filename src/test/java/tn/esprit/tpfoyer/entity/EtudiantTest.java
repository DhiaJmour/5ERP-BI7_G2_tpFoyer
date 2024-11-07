package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;


class EtudiantTest {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Date dateNaissance = formatter.parse("20-03-2000");
        Etudiant etudiant = new Etudiant("alice", "doe", 142021452L, dateNaissance);

    EtudiantTest() throws ParseException {
    }
    @Test
    void noArgConstructorShouldInitializeWithDefaultValues() {

        Etudiant etudiantDefault = new Etudiant();


        assertThat(etudiantDefault.getNomEtudiant()).isNull();
        assertThat(etudiantDefault.getPrenomEtudiant()).isNull();
        assertThat(etudiantDefault.getCinEtudiant()).isEqualTo(0l);
        assertThat(etudiantDefault.getDateNaissance()).isNull();
        assertThat(etudiantDefault.getReservations()).isNull();
    }



    @Test
    void getIdEtudiantShouldNothaveNullID() throws ParseException {
        assertThat(etudiant.getIdEtudiant()).isNotNull();
        assertThat(etudiant.getNomEtudiant()).isEqualTo("alice");
        assertThat(etudiant.getPrenomEtudiant()).isEqualTo("doe");
        assertThat(etudiant.getCinEtudiant()).isEqualTo(142021452L);
        Date dateNaissance = formatter.parse("20-03-2000");
        assertThat(etudiant.getDateNaissance()).isEqualTo(dateNaissance);
        assertThat(etudiant.getReservations()).isNull();


        }

    @Test
    void toStringShouldBeTested() {
        String expectedToString = "Etudiant{id=" + etudiant.getIdEtudiant() +
                ", nom=" + etudiant.getNomEtudiant() +
                ", prenom=" + etudiant.getPrenomEtudiant() +
                ", cin=" + etudiant.getCinEtudiant() +
                ", dateNaissance=" + etudiant.getDateNaissance()+"}";

        assertThat(etudiant.toString()).isEqualTo(expectedToString);
    }

}