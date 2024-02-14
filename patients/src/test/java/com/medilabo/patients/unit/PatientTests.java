package com.medilabo.patients.unit;

import com.medilabo.patients.TestVariables;
import com.medilabo.patients.domain.Patient;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Patient.class)
public class PatientTests extends TestVariables {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class validationTests {

        @Test
        public void validationTest() {
            Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
            assertTrue(violations.isEmpty());
        }

        @Nested
        public class firstNameTests {

            @Test
            public void firstNameNotNull() {
                patient.setFirstName(null);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }

            @Test
            public void firstNameSize() {
                patient.setFirstName(string46);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }
        }

        @Nested
        public class lastNameTests {

            @Test
            public void lastNameNotNull() {
                patient.setLastName(null);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }

            @Test
            public void lastNameSize() {
                patient.setLastName(string46);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }
        }

        @Nested
        public class birthDateTests {

            @Test
            public void birthDateNotNull() {
                patient.setBirthDate(null);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }

            @Test
            public void birthDatePast() {
                patient.setBirthDate(dateFuture);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }
        }
        
        @Nested
        public class genderTests {
            
            @Test
            public void genderNotNull() {
                patient.setGender(null);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }
        }

        @Nested
        public class addressTests {

            @Test
            public void addressSize() {
                patient.setAddress(string46);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }
        }

        @Nested
        public class phoneNumberTests {

            @Test
            public void phoneNumberNull() {
                patient.setPhoneNumber(null);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertTrue(violations.isEmpty());
            }

            @Test
            public void phoneNumberSizeTooBig() {
                patient.setPhoneNumber(string46);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }

            @Test
            public void phoneNumberSizeTooSmall() {
                patient.setPhoneNumber(string9);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }

            @Test
            public void phoneNumberNonNumerical() {
                patient.setPhoneNumber(string10NonNumerical);
                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
                assertFalse(violations.isEmpty());
            }
        }
    }
}
