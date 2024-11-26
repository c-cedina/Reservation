package CTHCC.Reservation.controllers;

import CTHCC.Reservation.models.Availability;
import CTHCC.Reservation.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    // Create or Update
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Availability saveAvailability(@RequestBody Availability availability) {
        return availabilityService.saveAvailability(availability);
    }

    // Read by ID
    @GetMapping("/{id}")
    public Optional<Availability> getAvailabilityById(@PathVariable Long id) {
        return availabilityService.getAvailabilityById(id);
    }

    // Read all
    @GetMapping
    public List<Availability> getAllAvailabilities() {
        return availabilityService.getAllAvailabilities();
    }

    // Read by Date
    @GetMapping("/date/{date}")
    public List<Availability> getAvailabilitiesByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return availabilityService.getAvailabilitiesByDate(localDate);
    }

    // Delete
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
    }
}
