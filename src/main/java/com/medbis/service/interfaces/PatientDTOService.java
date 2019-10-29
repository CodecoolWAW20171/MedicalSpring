package com.medbis.service.interfaces;

import com.medbis.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientDTOService {
    List<? extends User> findAll();
}



