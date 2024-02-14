package com.medilabo.patients.unit;

import com.medilabo.patients.TestVariables;
import com.medilabo.patients.controller.PatientController;
import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PatientController.class)
public class PatientControllerTests extends TestVariables {

    @Autowired
    PatientController patientController;

    @MockBean
    PatientService patientService;

    @MockBean
    BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        patient.setIdPatient(1);
        when(patientService.findById(any(Integer.class))).thenReturn(Optional.of(patient));
        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class addPatientTests {
        @Test
        public void addPatientTest() {
            assertTrue(patient.equals(patientController.addPatient(patient, bindingResult).getBody()));
        }

        @Test
        public void addPatientTestIfNotPatientValid() {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals(HttpStatus.BAD_REQUEST, patientController.addPatient(patient, bindingResult).getStatusCode());
        }
    }

    @Nested
    public class getPatientByIdTests {
        @Test
        public void getPatientByIdTest() {
            assertTrue(patient.equals(patientController.getPatientById(patient.getIdPatient()).getBody()));
        }
    }

    @Nested
    public class updatePatientTests {
        @Test
        public void updatePatientTest() {
            assertTrue(patient.equals(patientController.updatePatient(patient, bindingResult, patient.getIdPatient()).getBody()));
        }

        @Test
        public void updatePatientTestIfNotValidPatient() {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals(HttpStatus.BAD_REQUEST, patientController.updatePatient(patient, bindingResult, patient.getIdPatient()).getStatusCode());
        }

        @Test
        public void updatePatientTestIfPatientNotInDB() {
            when(patientService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> patientController.updatePatient(patient, bindingResult, patient.getIdPatient()));
        }
    }
}
