package com.medilabo.patients;

import com.medilabo.patients.domain.Patient;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.lang.String;

public class TestVariables {
    public Patient patient;
    public Integer patientId;
    public String string46;
    public String string9;
    public String string10NonNumerical;
    public Date datePast;
    public Date dateFuture;

    public void initializeVariables() {
        // objects used for validation tests
        string46 = "0".repeat(46);
        string9 = "0".repeat(9);
        string10NonNumerical = "X".repeat(10);
        datePast = Date.valueOf(LocalDate.now().minusYears(1));
        dateFuture = Date.valueOf(LocalDate.now().plusYears(1));

        // valid patient used for tests
        patient = new Patient("firstName", "lastName", datePast, Patient.Gender.OTHER, "address", "0123456789");
    }
}
