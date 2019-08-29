package com.medbis.repository;

import com.medbis.entity.Patient;
import com.medbis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    User findByName(String name);
}
