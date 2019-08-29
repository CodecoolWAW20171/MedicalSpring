package com.medbis.service.impl;

import com.medbis.entity.Disease;
import com.medbis.repository.DiseaseRepository;
import com.medbis.service.interfaces.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    private DiseaseRepository diseaseRepository;

    @Autowired
    public DiseaseServiceImpl(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }


    @Override
    public List<Disease> findAll() {
        return diseaseRepository.findAll();
    }

    public Disease findById(int id){
        Optional<Disease> disease =  this.diseaseRepository.findById(id);
        if(!disease.isPresent()){
            throw new RuntimeException("item with" + id + "not found");
        }
        return disease.get();
    }

    @Override
    public void deleteById(int id) {
        this.diseaseRepository.deleteById(id);
    }

    @Override
    public void save(Disease disease) {
        this.diseaseRepository.save(disease);
    }
}
