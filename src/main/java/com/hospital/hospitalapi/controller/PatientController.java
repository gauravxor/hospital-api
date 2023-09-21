package com.hospital.hospitalapi.controller;

import com.hospital.hospitalapi.entity.Patient;
import com.hospital.hospitalapi.service.PatientService;
import com.hospital.hospitalapi.utility.InputValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Add a new patient")
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Request body is invalid"), @ApiResponse(responseCode = "500", description = "Something went wrong while adding the patient"), @ApiResponse(responseCode = "201", description = "Patient Added")})
    public String addPatient(@RequestBody Patient patient, HttpServletResponse response) {
        String validationResult = (new InputValidator().validate(patient));
        if (!validationResult.equals("valid")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return validationResult;
        }
        String additionResult = patientService.addPatient(patient);
        if (additionResult.equals("Added")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return "Patient Added";
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Something went wrong while adding the patient";
        }
    }

    @DeleteMapping
    @Operation(summary = "Delete a patient")
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Patient Id cannot be empty"), @ApiResponse(responseCode = "404", description = "Invalid patientId or patient not found"), @ApiResponse(responseCode = "204", description = "Patient details Deleted"), @ApiResponse(responseCode = "500", description = "Something went wrong in the server :)")})
    public String deletePatient(@RequestParam Long patientId, HttpServletResponse response) {
        if (patientId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Invalid request";
        }
        String deleteResult = patientService.deletePatient(patientId);
        if (deleteResult.equals("Deleted")) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return "Patient Deleted";
        } else if (deleteResult.equals("Invalid patientId")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "Invalid patientId";
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Something went wrong while deleting the patient";
        }
    }
}