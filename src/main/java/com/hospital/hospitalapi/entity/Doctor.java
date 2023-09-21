package com.hospital.hospitalapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Schema(hidden = true)
    private Long doctorId;

    @Size(min = 3, max = 30)
    @NotEmpty
    private String name;

    @Size(min = 3, max = 20)
    @NotEmpty
    private String city;

    @NotEmpty
    private String email;

    @Size(min = 10, max = 10)
    @NotEmpty
    private Long phoneNo;

    @NotEmpty
    private String speciality;
    public Doctor() {

    }

    public Doctor(String name, String city, String email, Long phoneNo, String speciality) {
        this.name = name.toLowerCase();
        this.city = city.toLowerCase();
        this.email = email.toLowerCase();
        this.phoneNo = phoneNo;
        this.speciality = speciality.toLowerCase();
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setCity(String city) {
        this.city = city.toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality.toLowerCase();
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId = " + doctorId +
                ", name = '" + name + '\'' +
                ", city = '" + city + '\'' +
                ", email = '" + email + '\'' +
                ", phoneNo = " + phoneNo +
                ", speciality = '" + speciality + '\'' +
                '}';
    }
}