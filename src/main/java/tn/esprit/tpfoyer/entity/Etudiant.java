package tn.esprit.tpfoyer.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor


@NoArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idEtudiant;

    String nomEtudiant;
    String prenomEtudiant;
    long cinEtudiant;
    Date dateNaissance;

    @ManyToMany(mappedBy = "etudiants")
    Set<Reservation> reservations;
    public Etudiant(String nomEtudiant, String prenomEtudiant, long cinEtudiant, Date dateNaissance) {
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.cinEtudiant = cinEtudiant;
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "Etudiant{id=" + idEtudiant +
                ", nom=" + nomEtudiant +
                ", prenom=" + prenomEtudiant +
                ", cin=" + cinEtudiant +
                ", dateNaissance=" + dateNaissance + "}";
    }
}



