package com.medilabo.patients.service;

import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
            return patientRepository.save(patient);
    }

    public Optional<Patient> findById(Integer id) {
        return patientRepository.findById(id);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}