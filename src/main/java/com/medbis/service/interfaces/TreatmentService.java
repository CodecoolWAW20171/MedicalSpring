package com.medbis.service.interfaces;

import com.medbis.entity.Treatment;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TreatmentService {

List<Treatment> findAll();

Treatment findById(int id);

void deleteById(int id);

void save(Treatment treatment);

List<Treatment> findAllByCategoryId(int categoryId);
}
