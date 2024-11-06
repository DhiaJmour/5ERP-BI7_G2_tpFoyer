package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceMockitoTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setIdReservation("123");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
    }

    @Test
    void retrieveAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        when(reservationRepository.findAll()).thenReturn(reservations);

        List<Reservation> result = reservationService.retrieveAllReservations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void retrieveReservation() {
        when(reservationRepository.findById("123")).thenReturn(Optional.of(reservation));

        Reservation result = reservationService.retrieveReservation("123");

        assertNotNull(result);
        assertEquals("123", result.getIdReservation());
        verify(reservationRepository, times(1)).findById("123");
    }

    @Test
    void retrieveReservation_NotFound() {
        when(reservationRepository.findById("999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            reservationService.retrieveReservation("999");
        });

        assertEquals("Foyer not found with ID: 999", exception.getMessage());
        verify(reservationRepository, times(1)).findById("999");
    }

    @Test
    void addReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.addReservation(reservation);

        assertNotNull(result);
        assertEquals("123", result.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void modifyReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.modifyReservation(reservation);

        assertNotNull(result);
        assertEquals("123", result.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void removeReservation() {
        reservationService.removeReservation("123");
        verify(reservationRepository, times(1)).deleteById("123");
    }

    @Test
    void trouverResSelonDateEtStatus() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        Date date = new Date();

        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(any(Date.class), any(Boolean.class)))
                .thenReturn(reservations);

        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(date, true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(date, true);
    }
}

