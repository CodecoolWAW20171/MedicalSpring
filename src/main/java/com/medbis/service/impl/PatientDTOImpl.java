package com.medbis.service.impl;

import com.medbis.entity.User;
import com.medbis.repository.PatientRepository;
import com.medbis.service.interfaces.PatientDTOService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PatientDTOImpl implements PatientDTOService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientDTOImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<? extends User> findAll() {
        return patientRepository.findAll();
    }
}

