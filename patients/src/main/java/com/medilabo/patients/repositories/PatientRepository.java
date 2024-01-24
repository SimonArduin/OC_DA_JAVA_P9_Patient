package com.medilabo.patients.repositories;

import com.medilabo.patients.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
