package com.medilabo.patients.controller;

import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping("add")
    @ResponseBody
    public Patient addPatient(@Valid @RequestBody Patient patient, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            return patientService.savePatient(patient);
        }
        return null;
    }

    @GetMapping("get")
    @ResponseBody
    public Patient getPatientById(Integer id) {
        return patientService.findById(id).get();
    }

    @PostMapping("update/{id}")
    @ResponseBody
    public Patient updatePatient(@Valid @RequestBody Patient patient, BindingResult bindingResult, @PathVariable("id") Integer id) {
        if(!bindingResult.hasErrors()) {
            patientService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid patient Id:" + id)));
            patient.setIdPatient(id);
            return patientService.savePatient(patient);
        }
        return null;
    }
}
