package com.hospital.hospitalapi.service;

import com.hospital.hospitalapi.entity.Doctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    private final SessionFactory sessionFactory;

    DoctorService() {
        Configuration cfg = new Configuration();
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory();
    }

    private Session openSessionWithTransaction() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public String addDoctor(Doctor doctor) {
        Session session = null;
        try {
            session = openSessionWithTransaction();
            session.persist(doctor);
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

    public String deleteDoctor(Long doctorId) {
        Session session = null;
        try {
            session = openSessionWithTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);
            if (doctor != null) {
                session.remove(doctor);
                session.getTransaction().commit();
                return "Deleted";
            } else {
                return "Invalid doctorId";
            }
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
}