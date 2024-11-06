package tn.esprit.tpfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceJUnitTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setIdReservation("456");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
        reservationRepository.save(reservation); // Saving for integration tests
    }

    @Test
    @Order(1)
    void retrieveAllReservations() {
        List<Reservation> reservations = reservationService.retrieveAllReservations();

        assertNotNull(reservations);
        assertTrue(reservations.size() > 0);
    }

    @Test
    @Order(2)
    void retrieveReservation() {
        Reservation foundReservation = reservationService.retrieveReservation("456");

        assertNotNull(foundReservation);
        assertEquals("456", foundReservation.getIdReservation());
    }

    @Test
    @Order(3)
    void removeReservation() {
        reservationService.removeReservation("456");

        assertThrows(EntityNotFoundException.class, () -> {
            reservationService.retrieveReservation("456");
        });
    }

    // Additional tests can be added for other methods if necessary
}

