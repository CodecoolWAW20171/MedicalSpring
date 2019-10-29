package com.medbis.service.impl;

import com.medbis.entity.Patient;
import com.medbis.entity.User;
import com.medbis.repository.PatientRepository;
import com.medbis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "PatientServiceImpl")
public class PatientServiceImpl implements UserService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<? extends User> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(int id) {
        Optional<Patient> result = patientRepository.findById(id);
        Patient patient;
        if(result.isPresent()){
            patient = result.get();
        }else{
            throw new RuntimeException("Dod not find patient with id: " + id);
        }
        return patient;
    }

    @Override
    public void save(User user) {
        patientRepository.save((Patient) user);
    }

    @Override
    public void deleteById(int theId) {
        patientRepository.deleteById(theId);
    }


    @Override
    public User findByName(String name) {
      return patientRepository.findByName(name);
    }

}
