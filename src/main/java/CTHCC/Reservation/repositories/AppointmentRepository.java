package CTHCC.Reservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CTHCC.Reservation.enums.Status;
import CTHCC.Reservation.models.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Trouver les rendez-vous par date
    List<Appointment> findByDate(LocalDate date);

    // Trouver les rendez-vous par statut
    List<Appointment> findByStatus(Status status);

    // Trouver les rendez-vous d'une disponibilité spécifique
    List<Appointment> findByAvailabilityId(Long availabilityId);

    // Trouver les rendez-vous par date et statut
    List<Appointment> findByDateAndStatus(LocalDate date, Status status);

    // Trouver les rendez-vous entre deux date
    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);
}
