package com.medilabo.patients.UnitTests;

import com.medilabo.patients.TestVariables;
import com.medilabo.patients.controller.PatientController;
import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.service.PatientService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
            JSONObject expected = patient.toJson();
            JSONObject actual = patientController.addPatient(patient, bindingResult);
            assertEquals(expected.toString(), actual.toString());
        }

        @Test
        public void addPatientTestIfNotPatientValid() {
            when(bindingResult.hasErrors()).thenReturn(true);
            JSONObject expected = new JSONObject();
            JSONObject actual = patientController.addPatient(patient, bindingResult);
            assertEquals(expected.toString(), actual.toString());
        }
    }

    @Nested
    public class getPatientByIdTests {
        @Test
        public void getPatientByIdTest() {
            JSONObject expected = patient.toJson();
            JSONObject actual = patientController.getPatientById(patient.getIdPatient());
            assertEquals(expected.toString(), actual.toString());
        }
    }

    @Nested
    public class updatePatientTests {
        @Test
        public void updatePatientTest() {
            JSONObject expected = patient.toJson();
            JSONObject actual = patientController.updatePatient(patient, bindingResult, patient.getIdPatient());
            assertEquals(expected.toString(), actual.toString());
        }

        @Test
        public void updatePatientTestIfNotValidPatient() {
            when(bindingResult.hasErrors()).thenReturn(true);
            JSONObject expected = new JSONObject();
            JSONObject actual = patientController.updatePatient(patient, bindingResult, patient.getIdPatient());
            assertEquals(expected.toString(), actual.toString());
        }

        @Test
        public void updatePatientTestIfPatientNotInDB() {
            when(patientService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> patientController.updatePatient(patient, bindingResult, patient.getIdPatient()));
        }
    }
}
