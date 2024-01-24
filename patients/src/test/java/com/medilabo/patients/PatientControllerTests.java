package com.medilabo.patients;

import com.medilabo.patients.controller.PatientController;
import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.repositories.PatientRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PatientController.class)
public class PatientControllerTests extends TestVariables {

    @Autowired
    PatientController patientController;

    @MockBean
    PatientRepository patientRepository;

    @MockBean
    BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        patient.setIdPatient(1);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
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
}
