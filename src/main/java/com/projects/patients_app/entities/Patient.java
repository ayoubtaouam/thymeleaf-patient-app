package com.projects.patients_app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Patients")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 2, max = 20)
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 20)
    private String lastName;
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;
    private boolean ill;
    @Min(10)
    private int score;
}
