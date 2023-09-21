package com.hospital.hospitalapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Schema(hidden = true)
    private Long patientId;

    @Size(min = 3, max = 30)
    @NotNull
    private String name;

    @Size(min = 3, max = 20)
    @NotNull
    private String city;
    @NotNull
    private String email;
    @Size(min = 10, max = 10)
    @NotNull
    private Long phoneNo;
    @NotNull
    private String symptom;

    public Patient() {

    }

    public Patient(String name, String city, String email, Long phoneNo, String symptom) {
        this.name = name.trim().toLowerCase();
        this.city = city.trim().toLowerCase();
        this.email = email.trim().toLowerCase();
        this.phoneNo = phoneNo;
        this.symptom = symptom.trim().toLowerCase();
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setName(String name) {
        this.name = name.trim().toLowerCase();
    }

    public void setCity(String city) {
        this.city = city.trim().toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom.trim().toLowerCase().trim();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", symptom='" + symptom + '\'' +
                '}';
    }
}