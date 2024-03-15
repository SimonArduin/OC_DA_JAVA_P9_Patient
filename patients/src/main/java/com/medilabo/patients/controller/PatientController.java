package com.medilabo.patients.controller;

import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {

    @Autowired
    PatientService patientService;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    /**
     * This method adds a Patient to the database, if it is valid
     * @param patient
     * @param bindingResult
     * @return A Response Entity
     */
    @PostMapping("add")
    public ResponseEntity addPatient(@Valid @RequestBody Patient patient, BindingResult bindingResult) {

        // if patient is valid, add it to database
        if(!bindingResult.hasErrors()) {
            logger.info("received request to add patient " + patient.getFirstName() + " " + patient.getLastName());
            return ResponseEntity.created(null).body(patientService.savePatient(patient));
        }

        // else, log errors
        logger.error("received incorrect request to add patient : " + patient.toJson() + ", errors are : " + bindingResult.getFieldErrors());
        String errorMessage = new String();
        for(FieldError error : bindingResult.getFieldErrors()) {
            errorMessage = errorMessage + "error in field : " + error.getField() + ", rejected value : " + error.getRejectedValue() + "\n";
        }
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method returns a specific Patient
     * @param id
     * @return A ResponseEntity with a Patient in its body
     */
    @GetMapping("get")
    public ResponseEntity getPatientById(Integer id) {
        logger.info("received request to get patient " + id);
        return ResponseEntity.ok().body(patientService.findById(id).get());
    }

    /**
     * This method returns all patients in the database
     * @return A ResponseEntity with a List of Patient in its body
     */
    @GetMapping("getall")
    public ResponseEntity getAll() {
        logger.info("received request to get all patients ");
        return ResponseEntity.ok().body(patientService.findAll());
    }

    /**
     * This method updates the informations of a specific patient
     * @param patient
     * @param bindingResult
     * @param id
     * @return A ResponseEntity
     */
    @PutMapping("update/{id}")
    public ResponseEntity updatePatient(@Valid @RequestBody Patient patient, BindingResult bindingResult, @PathVariable("id") Integer id) {

        // if patient is valid, update it in database
        if(!bindingResult.hasErrors()) {
            patientService.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid patient Id:" + id)));
            patient.setIdPatient(id);
            logger.info("received request to update patient " + patient.getIdPatient());
            return ResponseEntity.created(null).body(patientService.savePatient(patient));
        }

        // else, log errors
        logger.error("received incorrect request to update patient " + id + ", errors are : " + bindingResult.getFieldErrors());
        String errorMessage = new String();
        for(FieldError error : bindingResult.getFieldErrors()) {
            errorMessage = errorMessage + "error in field : " + error.getField() + ", rejected value : " + error.getRejectedValue() + "\n";
        }
        return ResponseEntity.badRequest().build();
    }
}
