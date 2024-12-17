package com.projects.patients_app.security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity @AllArgsConstructor @NoArgsConstructor @Builder @Setter @Getter
public class AppRole {
    @Id
    private String role;
}
