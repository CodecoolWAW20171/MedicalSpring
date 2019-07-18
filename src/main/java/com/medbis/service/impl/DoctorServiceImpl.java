package com.medbis.service.impl;

import com.medbis.entity.Doctor;
import com.medbis.repository.DoctorRepository;
import com.medbis.service.interfaces.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DoctorServiceImpl implements DoctorService {
    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor findById(int id) {
        Optional<Doctor> disease = this.doctorRepository.findById(id);
        if (!disease.isPresent()) {
            throw new RuntimeException("item with" + id + "not found");
        }
        return disease.get();
    }

    @Override
    public void deleteById(int id) {
        this.doctorRepository.deleteById(id);
    }

    @Override
    public void save(Doctor doctor) {
        this.doctorRepository.save(doctor);
    }
}

