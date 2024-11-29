package CTHCC.Reservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CTHCC.Reservation.models.Availability;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    // Trouver les disponibilités par date
    List<Availability> findByDate(LocalDate date);

    List<Availability> findAll();

    // Trouver les disponibilités entre deux dates
    List<Availability> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Availability> findByIsAvailable(boolean available);

    List<Availability> findByDateBetweenAndIsAvailable(LocalDate startDate, LocalDate endDate, boolean available);
}
