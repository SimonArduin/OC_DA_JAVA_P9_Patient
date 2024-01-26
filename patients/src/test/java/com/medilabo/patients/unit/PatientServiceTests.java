package com.medilabo.patients.unit;

import com.medilabo.patients.TestVariables;
import com.medilabo.patients.service.PatientService;
import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PatientService.class)
public class PatientServiceTests extends TestVariables {

    @Autowired
    PatientService patientService;

    @MockBean
    PatientRepository patientRepository;

    @MockBean
    BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        patient.setIdPatient(1);
        when(patientRepository.findById(any(Integer.class))).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class savePatientTests {
        @Test
        public void savePatientTest() {
            Patient actual = patientService.savePatient(patient);
            assertEquals(patient, actual);
        }
    }

    @Nested
    public class findByIdTests {
        @Test
        public void findByIdTest() {
            Patient actual = patientService.findById(patient.getIdPatient()).get();
            assertEquals(patient, actual);
        }
    }
}
