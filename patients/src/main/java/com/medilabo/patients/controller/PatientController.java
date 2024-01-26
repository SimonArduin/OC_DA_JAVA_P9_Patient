package com.medilabo.patients.controller;

import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.service.PatientService;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

@Controller
public class PatientController {

    @Autowired
    PatientService patientService;
    public JSONObject addPatient(@Valid Patient patient, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            return patientService.savePatient(patient).toJson();
        }
        return new JSONObject();
    }

    public JSONObject getPatientById(Integer id) {
        return patientService.findById(id).get().toJson();
    }

    public JSONObject updatePatient(@Valid Patient patient, BindingResult bindingResult, Integer id) {
        if(!bindingResult.hasErrors()) {
            patientService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid patient Id:" + id)));
            return patientService.savePatient(patient).toJson();
        }
        return new JSONObject();
    }
}
