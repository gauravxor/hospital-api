package com.hospital.hospitalapi.service;

import com.hospital.hospitalapi.entity.Doctor;
import com.hospital.hospitalapi.entity.Patient;
import com.hospital.hospitalapi.pojo.RecommendationResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;


public class RecommendationService {
    private final SessionFactory sessionFactory;

    public RecommendationService() {
        Configuration cfg = new Configuration();
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory();
    }

    private Session openSessionWithTransaction() {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        return session;
    }

    private String getSpeciality(String symptom) {
        return switch (symptom) {
            case "arthritis", "back pain", "tissue injuries" -> "orthopedic";
            case "dysmenorrhea" -> "gynecology";
            case "skin infection", "skin burn" -> "dermatology";
            case "ear pain" -> "ent";
            default -> null;
        };
    }

    public RecommendationResult getRecommendation(Long patientId) {
        Session session = null;
        List<Doctor> recommendations = new ArrayList<>();
        try {
            session = openSessionWithTransaction();
            Patient patient = session.get(Patient.class, patientId);
            if (patient == null) {
                return new RecommendationResult("Patient not found", null);
            }
            String city = patient.getCity();
            Query<Doctor> query = session.createQuery("SELECT d FROM Doctor d WHERE d.city = :city", Doctor.class);
            query.setParameter("city", city);
            List<Doctor> doctors = query.getResultList();
            if (doctors.isEmpty()) {
                return new RecommendationResult("We are still waiting to expand to your location", null);
            }
            for (Doctor doctor : doctors) {
                String speciality = doctor.getSpeciality();
                if (speciality.equals(getSpeciality(patient.getSymptom()))) {
                    recommendations.add(doctor);
                }
            }
            session.close();
            if (recommendations.isEmpty()) {
                return new RecommendationResult("There isn’t any doctor present at your location for your symptom", null);
            }
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            return new RecommendationResult("Something went wrong while fetching recommendations", null);
        }
        return new RecommendationResult("We found some doctors for you", recommendations);
    }
}