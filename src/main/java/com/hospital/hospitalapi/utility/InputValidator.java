package com.hospital.hospitalapi.utility;

import com.hospital.hospitalapi.entity.Doctor;
import com.hospital.hospitalapi.entity.Patient;

public class InputValidator {
//    private final List<String> specialities = List.of("orthopedic", "gynecology", "dermatology", "ent specialist");
//    private final List<String> symptoms = List.of(
//            "arthritis", "back pain", "tissue injuries",
//            "dysmenorrhea ", "skin infection", "skin burn",
//            "ear pain", "eye pain"
//    );

    private String validateName(String name) {
        if (name == null || name.isEmpty()) {
            return "Name cannot be empty";
        }
        if (name.length() < 3) {
            return "Name should be at least 3 characters long";
        }
        return "valid";
    }

    private String validateCity(String city) {
        if (city == null || city.isEmpty()) {
            return "City cannot be empty";
        }
        if (city.length() > 20) {
            return "City should be at most 20 characters long";
        }
        return "valid";
    }

    private String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "Email cannot be empty";
        }
        if (email.contains("@") && email.contains(".")) {
            String userName = email.substring(0, email.indexOf("@"));
            String domainName = email.substring(email.indexOf("@") + 1, email.indexOf("."));
            String extension = email.substring(email.indexOf(".") + 1);
            if (userName.isEmpty() || domainName.isEmpty() || extension.isEmpty()) {
                return "Invalid Email";
            }
            return "valid";
        }
        return "Invalid Email";
    }

    private String validatePhoneNo(Long phoneNo) {
        if (phoneNo == null) {
            return "Phone Number cannot be empty";
        }
        if (phoneNo.toString().length() != 10) {
            return "Phone Number should be 10 digits long";
        }
        return "valid";
    }

    private String validateDoctor(Doctor doctor) {
        if (!validateName(doctor.getName()).equals("valid")) {
            return validateName(doctor.getName());
        }
        if (!validateCity(doctor.getCity()).equals("valid")) {
            return validateCity(doctor.getCity());
        }
        if (!validateEmail(doctor.getEmail()).equals("valid")) {
            return validateEmail(doctor.getEmail());
        }
        if (!validatePhoneNo(doctor.getPhoneNo()).equals("valid")) {
            return validatePhoneNo(doctor.getPhoneNo());
        }

        if (doctor.getSpeciality() == null || doctor.getSpeciality().isEmpty()) {
            return "Speciality cannot be empty";
        }
//        if (!specialities.contains(doctor.getSpeciality())) {
//            return "Invalid Speciality";
//        }
        return "valid";
    }

    private String validatePatient(Patient patient) {
        if (!validateName(patient.getName()).equals("valid")) {
            return validateName(patient.getName());
        }
        if (!validateCity(patient.getCity()).equals("valid")) {
            return validateCity(patient.getCity());
        }
        if (!validateEmail(patient.getEmail()).equals("valid")) {
            return validateEmail(patient.getEmail());
        }
        if (!validatePhoneNo(patient.getPhoneNo()).equals("valid")) {
            return validatePhoneNo(patient.getPhoneNo());
        }

        if (patient.getSymptom() == null || patient.getSymptom().isEmpty()) {
            return "Symptom cannot be empty";
        }
//        if (!symptoms.contains(patient.getSymptom())) {
//            return "Invalid Symptom";
//        }
        return "valid";
    }

    public <T> String validate(T user) {
        if (user instanceof Doctor) {
            return validateDoctor((Doctor) user);
        } else if (user instanceof Patient) {
            return validatePatient((Patient) user);
        } else {
            return "Invalid Request";
        }
    }
}