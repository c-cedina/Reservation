package CTHCC.Reservation.services;

import CTHCC.Reservation.models.Appointment;
import CTHCC.Reservation.models.Availability;
import CTHCC.Reservation.repositories.AppointmentRepository;
import CTHCC.Reservation.repositories.AvailabilityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    // Create or Update
    public Appointment saveAppointment(Appointment appointment) {
        if (appointment.getAvailability() == null || appointment.getAvailability().getId() == null) {
            throw new IllegalArgumentException("Availability must not be null or missing an ID.");
        }

        // Vérifier si l'Availability existe dans la base de données
        Availability availability = availabilityRepository.findById(appointment.getAvailability().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Availability not found with ID: " + appointment.getAvailability().getId()));

        // Associer l'Availability persistée à l'Appointment
        appointment.setAvailability(availability);

        // Sauvegarder l'Appointment
        return appointmentRepository.save(appointment);
    }

    // Read by ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // Read all
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Delete
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
