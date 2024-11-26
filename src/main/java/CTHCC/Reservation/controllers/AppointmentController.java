package CTHCC.Reservation.controllers;

import CTHCC.Reservation.models.Appointment;
import CTHCC.Reservation.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Create or Update
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public Appointment saveAppointment(@RequestBody Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    // Read by ID
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public Optional<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    // Read all
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Read by Date Range
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/range")
    public List<Appointment> getAppointmentsByDateRange(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDateTime startDateTime = LocalDateTime.parse(start);
        LocalDateTime endDateTime = LocalDateTime.parse(end);
        return appointmentService.getAppointmentsByDateRange(startDateTime, endDateTime);
    }

    // Delete
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}
