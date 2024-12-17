package com.projects.patients_app.repositories;

import com.projects.patients_app.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(String firstName, String lastName, Pageable pageable);
}
