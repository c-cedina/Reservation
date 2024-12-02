package CTHCC.Reservation.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;

import CTHCC.Reservation.enums.Status;

@Entity
@Table(name = "appointment", uniqueConstraints = @UniqueConstraint(columnNames = "availability_id") // Rend cette //
                                                                                                    // colonne unique
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "availability_id", nullable = false)
    @JsonBackReference
    private Availability availability;
}
