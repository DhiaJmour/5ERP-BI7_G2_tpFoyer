package tn.esprit.tpfoyer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;

import java.util.List;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {





























// Trouver toutes les chambres qui sont de typ SIMPlE :



    List<Chambre> findAllByTypeC(TypeChambre tc);




    /* No need to code CRUD here. Its is already in the
    interfaces provided by the framework Spring Data JPA :
       - CrudRepository or
       - PagingAndSortingRepository or
       - JpaRepository
     */





    Chambre findChambreByNumeroChambre(Long num);





    // Recperer la chambre selon le CIN de l'étudiant qui l'occupe :
    @Query("SELECT ch FROM Chambre ch " +
            "INNER JOIN ch.reservations r " +
            "INNER JOIN r.etudiants e " +
            "WHERE e.cinEtudiant=:cin ")
    Chambre trouverChselonEt(long cin);




// Compter toutes les chambres d'un certain type
long countByTypeC(TypeChambre typeC);

// Compter les chambres d'un type donné qui sont disponibles
long countByTypeCAndDisponible(TypeChambre typeC, boolean disponible);

// Rechercher les chambres avec un prix inférieur ou égal au prixMax et une taille supérieure ou égale à tailleMin
List<Chambre> findByPrixLessThanEqualAndTailleGreaterThanEqual(double prixMax, double tailleMin);



}
