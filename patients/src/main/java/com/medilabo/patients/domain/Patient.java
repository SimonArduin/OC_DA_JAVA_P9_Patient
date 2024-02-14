package com.medilabo.patients.domain;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.json.JSONObject;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="idpatient")
    private int idPatient;

    @NotNull
    @Size(max = 45)
    @Column(name="firstname")
    private String firstName;

    @NotNull
    @Size(max = 45)
    @Column(name="lastname")
    private String lastName;

    @NotNull
    @Past
    @Column(name="birthdate")
    private Date birthDate;

    @NotNull
    private Gender gender;
    public enum Gender {
        MALE, FEMALE, OTHER
    }

    @Size(max = 45)
    private String address;

    @Pattern(regexp = "^$|\\d{10}")
    @Column(name="phonenumber")
    private String phoneNumber;

    public Patient(String firstName, String lastName, Date birthDate, Gender gender, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient() {}

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public JSONObject toJson() {
        JSONObject patientJson = new JSONObject();
        patientJson.put("idPatient", this.idPatient);
        patientJson.put("firstName", this.firstName);
        patientJson.put("lastName", this.lastName);
        patientJson.put("birthDate", this.birthDate);
        patientJson.put("gender", this.gender.toString());
        patientJson.put("address", this.address);
        patientJson.put("phoneNumber", this.phoneNumber);

        return patientJson;
    }
}
