package CTHCC.Reservation.services;

import CTHCC.Reservation.models.Availability;
import CTHCC.Reservation.repositories.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    /**
     * Fetch availabilities by date.
     * 
     * @param date The date for which availabilities are to be fetched.
     * @return A list of availabilities for the given date.
     */
    public List<Availability> getAvailabilitiesByDate(LocalDate date) {
        return availabilityRepository.findByDate(date);
    }

    /**
     * Fetch all availabilities.
     * 
     * @return A list of all availabilities.
     */
    public List<Availability> getAllAvailabilities() {
        return availabilityRepository.findAll();
    }

    /**
     * Fetch availability by ID.
     * 
     * @param id The ID of the availability.
     * @return An Optional containing the availability if found.
     */
    public Optional<Availability> getAvailabilityById(long id) {
        return availabilityRepository.findById(id);
    }

    /**
     * Create or update an availability.
     * 
     * @param availability The availability to be saved.
     * @return The saved availability object.
     */
    public Availability saveOrUpdate(Availability availability) {
        return availabilityRepository.save(availability);
    }

    public void deleteAvailability(long id) {
        availabilityRepository.deleteById(id);
    }

    /**
     * Delete all availabilities.
     * Useful for cleanup or testing purposes.
     */
    public void deleteAll() {
        availabilityRepository.deleteAll();
    }

    public Availability saveAvailability(Availability availability) {
        return availabilityRepository.save(availability);
    }
}
