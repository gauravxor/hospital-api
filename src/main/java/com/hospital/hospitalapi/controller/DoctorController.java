package com.hospital.hospitalapi.controller;

import com.hospital.hospitalapi.entity.Doctor;
import com.hospital.hospitalapi.service.DoctorService;
import com.hospital.hospitalapi.utility.InputValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Operation(summary = "Add a new doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Request body is invalid"),
            @ApiResponse(responseCode = "500", description = "Something went wrong while adding the doctor"),
            @ApiResponse(responseCode = "201", description = "Doctor Added")
    })
    public String addDoctor(@RequestBody Doctor doctor, HttpServletResponse response) {
        // validate the incoming request body having doctor details
        String validationResult = (new InputValidator().validate(doctor));
        if (!validationResult.equals("valid")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return validationResult;
        }

        // add the doctor to the database
        String additionResult = doctorService.addDoctor(doctor);
        if (additionResult.equals("Added")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return "Doctor Added";
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Something went wrong in the server";
        }
    }

    @DeleteMapping
    @Operation(summary = "Delete a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Doctor Id cannot be empty"),
            @ApiResponse(responseCode = "404", description = "Invalid doctorId or doctor not found"),
            @ApiResponse(responseCode = "204", description = "Doctor details Deleted"),
            @ApiResponse(responseCode = "500", description = "Something went wrong in the server :)")
    })
    public String deleteDoctor(@RequestParam Long doctorId, HttpServletResponse response) {
        if (doctorId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Invalid Request";
        }

        // delete the doctor from the database
        String deleteResult = doctorService.deleteDoctor(doctorId);
        if (deleteResult.equals("Deleted")) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return "Doctor Deleted";  // No Content
        } else if (deleteResult.equals("Invalid doctorId")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "Invalid doctorId";
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Something went wrong while deleting the doctor";
        }
    }
}