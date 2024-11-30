package CTHCC.Reservation.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import CTHCC.Reservation.enums.Status;

@Entity
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
