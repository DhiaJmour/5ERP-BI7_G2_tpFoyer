package tn.esprit.tpfoyer.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    void setUp() {
        reservation1 = new Reservation("1", new Date(), true, null);
        reservation2 = new Reservation("2", new Date(), false, null);
    }

    @Test
    void testGetReservations() throws Exception {
        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);
        Mockito.when(reservationService.retrieveAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/reservation/retrieve-all-reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idReservation").value("1"))
                .andExpect(jsonPath("$[1].idReservation").value("2"));
    }

    @Test
    void testRetrieveReservation() throws Exception {
        Mockito.when(reservationService.retrieveReservation("1")).thenReturn(reservation1);

        mockMvc.perform(get("/reservation/retrieve-reservation/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("1"))
                .andExpect(jsonPath("$.estValide").value(true));
    }

    @Test
    void testAddReservation() throws Exception {
        Mockito.when(reservationService.addReservation(any(Reservation.class))).thenReturn(reservation1);

        mockMvc.perform(post("/reservation/add-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("1"))
                .andExpect(jsonPath("$.estValide").value(true));
    }

    @Test
    void testModifyReservation() throws Exception {
        reservation1.setEstValide(false);
        Mockito.when(reservationService.modifyReservation(any(Reservation.class))).thenReturn(reservation1);

        mockMvc.perform(put("/reservation/modify-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("1"))
                .andExpect(jsonPath("$.estValide").value(false));
    }

    @Test
    void testRemoveReservation() throws Exception {
        doNothing().when(reservationService).removeReservation("1");

        mockMvc.perform(delete("/reservation/remove-reservation/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testRetrieveReservationByDateAndStatus() throws Exception {
        List<Reservation> reservations = Arrays.asList(reservation1);
        Mockito.when(reservationService.trouverResSelonDateEtStatus(any(Date.class), any(Boolean.class)))
                .thenReturn(reservations);

        mockMvc.perform(get("/reservation/retrieve-reservation-date-status/2023-12-31/true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idReservation").value("1"))
                .andExpect(jsonPath("$[0].estValide").value(true));
    }
}
