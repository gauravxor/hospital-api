package com.hospital.hospitalapi.pojo;
import com.hospital.hospitalapi.entity.Doctor;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.List;

@Hidden
public class RecommendationResult {
    private String message;
    private List<Doctor> doctors;
    public RecommendationResult() {
    }

    public RecommendationResult(String message, List<Doctor> doctors) {
        this.message = message;
        this.doctors = doctors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}