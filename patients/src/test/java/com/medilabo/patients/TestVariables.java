package com.medilabo.patients;

import com.medilabo.patients.domain.Patient;

import java.util.Date;
import java.lang.String;

public class TestVariables {
    public Patient patient;
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
        datePast = new Date();
        dateFuture = new Date(datePast.getTime() + 999999999);

        // valid patient used for tests
        patient = new Patient("firstName", "lastName", datePast, Patient.Gender.OTHER, "address", "0123456789");
    }
}
