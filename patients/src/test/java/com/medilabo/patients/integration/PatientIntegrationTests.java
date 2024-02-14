package com.medilabo.patients.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patients.TestVariables;
import com.medilabo.patients.domain.Patient;
import com.medilabo.patients.repository.PatientRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PatientIntegrationTests extends TestVariables {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PatientRepository patientRepository;

    Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        initializeVariables();
        patientRepository.save(patient);
        patientId = patient.getIdPatient();
    }

    @AfterAll
    public void cleanUpDatabase() {
        patientRepository.deleteById(patientId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        databaseSizeBefore = patientRepository.findAll().size();
    }

    private Integer databaseSizeChange() {
        return patientRepository.findAll().size() - databaseSizeBefore;
    }

    private Boolean resultEqualsPatient(MvcResult result, Patient patient) throws UnsupportedEncodingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Patient resultPatient = objectMapper.readValue(result.getResponse().getContentAsString(), Patient.class);
        
        return resultPatient.getFirstName().equals(patient.getFirstName())
                && resultPatient.getLastName().equals(patient.getLastName())
                && resultPatient.getBirthDate().toLocalDate().equals(patient.getBirthDate().toLocalDate())
                && resultPatient.getGender().equals(patient.getGender())
                && resultPatient.getAddress().equals(patient.getAddress())
                && resultPatient.getPhoneNumber().equals(patient.getPhoneNumber());
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class addPatientTests {
        @Test
        public void addPatientTest() throws Exception {
            MvcResult result = mockMvc.perform(post("/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(patient.toJson().toString()))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            patient.setIdPatient(patientId);
            assertTrue(resultEqualsPatient(result, patient));
            assertEquals(1, databaseSizeChange());
        }
    }

    @Nested
    public class getPatientByIdTests {
        @Test
        public void getPatientByIdTest() throws Exception {
            MvcResult result = mockMvc.perform(get("/get").param("id", String.valueOf(patientId)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            patient.setIdPatient(patientId);
            assertTrue(resultEqualsPatient(result, patient));
            assertEquals(0, databaseSizeChange());
        }
    }

   @Nested
    public class updatePatientTests {
       @Test
       public void updatePatientTest() throws Exception {
           MvcResult result = mockMvc.perform(put("/update/" + patientId)
                           .contentType(MediaType.APPLICATION_JSON)
                           .content(patient.toJson().toString()))
                   .andExpect(status().is2xxSuccessful())
                   .andReturn();

           patient.setIdPatient(patientId);
           assertTrue(resultEqualsPatient(result, patient));
           assertEquals(0, databaseSizeChange());
       }
    }
}
