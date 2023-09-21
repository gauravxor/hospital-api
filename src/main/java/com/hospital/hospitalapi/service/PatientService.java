package com.hospital.hospitalapi.service;

import com.hospital.hospitalapi.entity.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private final SessionFactory sessionFactory;

    public PatientService() {
        Configuration cfg = new Configuration();
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory();
    }

    private Session openSessionWithTransaction() {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        return session;
    }

    public String addPatient(Patient patient) {
        Session session = null;
        try {
            session = openSessionWithTransaction();
            session.persist(patient);
            session.getTransaction().commit();
            return "Added";
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return "Failed";
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public String deletePatient(Long patientId) {
        Session session = null;
        try {
            session = openSessionWithTransaction();
            Patient patient = session.get(Patient.class, patientId);
            if (patient != null) {
                session.remove(patient);
                session.getTransaction().commit();
                return "Deleted";
            } else {
                return "Invalid patientId";
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
            return "Failed";
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}