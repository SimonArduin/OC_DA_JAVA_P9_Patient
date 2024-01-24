package com.medilabo.patients.controller;

import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.repositories.PatientRepository;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

public class PatientController {

    @Autowired
    PatientRepository patientRepository;

    //TODO : add CRUD

    //CREATE
    public JSONObject addPatient(@Valid Patient patient, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            return patientRepository.save(patient).toJson();
        }
        return new JSONObject();
    };

    //READ
    public JSONObject getPatientById(Integer id) {
        return patientRepository.findById(id).get().toJson();
    };

    //UPDATE
}
