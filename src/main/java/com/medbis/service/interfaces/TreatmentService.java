package com.medbis.service.interfaces;

import com.medbis.entity.Treatment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface TreatmentService {

List<Treatment> findAll();

Treatment findById(int id);

void deleteById(int id);

void save(Treatment treatment);

List<Treatment> findAllByCategoryId(int categoryId);


HashMap<String, List<HashMap<String, Object>>> findAllTreatmentsByCategory();

Integer getTotalTreatmentsDoneEmployee(int id);

Integer countByEmployeeByMonth(int id, LocalDate firstDayOfMonthDate, LocalDate lastDayOfMonthDate);

    Map<Integer, Integer> getAmountSingleTreatmentDoneEmployee();
}
