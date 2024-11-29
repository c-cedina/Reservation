package CTHCC.Reservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CTHCC.Reservation.enums.Status;
import CTHCC.Reservation.models.Appointment;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Trouver les rendez-vous par statut
    List<Appointment> findByStatus(Status status);

    // Trouver les rendez-vous d'une disponibilité spécifique
    List<Appointment> findByAvailabilityId(Long availabilityId);

}
