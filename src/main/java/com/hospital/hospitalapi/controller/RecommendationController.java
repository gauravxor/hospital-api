package com.hospital.hospitalapi.controller;


import com.hospital.hospitalapi.pojo.RecommendationResult;
import com.hospital.hospitalapi.service.RecommendationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommend")
public class RecommendationController {
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Patient Id cannot be empty"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "200", description = "Recommendation Result"),
            @ApiResponse(responseCode = "500", description = "Something went wrong in the server :)")
    })
    public RecommendationResult getRecommendation(@RequestParam Long patientId, HttpServletResponse response) {
        if (patientId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new RecommendationResult("Invalid Request", null);
        }
        RecommendationResult result = new RecommendationService().getRecommendation(patientId);
        if (result.getMessage().equals("Patient not found")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (result.getMessage().equals("Something went wrong while fetching recommendations")) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return result;
    }
}