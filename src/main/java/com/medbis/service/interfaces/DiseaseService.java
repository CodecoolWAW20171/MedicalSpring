package com.medbis.service.interfaces;

import com.medbis.entity.Disease;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface DiseaseService {

    List<Disease> findAll();

    Disease findById(int id);

    void deleteById(int id);


    void save(Disease disease);

}
