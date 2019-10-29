package com.medbis.service.impl;

import com.medbis.entity.Treatment;
import com.medbis.repository.TreatmentRepository;
import com.medbis.service.interfaces.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private TreatmentRepository treatmentRepository;

    @Autowired
    public TreatmentServiceImpl(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public List<Treatment> findAll() {
        return this.treatmentRepository.findAll();
    }

    public HashMap<String, List<HashMap<String, Object>>> findAllTreatmentsByCategory(){

        HashMap<String, List<HashMap<String, Object>>> categoryAndTreatments = new HashMap<>();

        List<TreatmentRepository.TreatAndCatDTO> allTreatments = treatmentRepository.findAllTreatmentsAndCategory();

        for (TreatmentRepository.TreatAndCatDTO treatment : allTreatments) {
            String categoryName =  treatment.getCategoryName();

            if(categoryAndTreatments.containsKey(categoryName)){
                HashMap<String, Object> treatmentNameAndId = new HashMap<>();
                treatmentNameAndId.put("treatmentName", treatment.getServiceName());
                treatmentNameAndId.put("treatmentId", treatment.getServiceID());

                List<HashMap<String, Object>> treatmentList = categoryAndTreatments.get(categoryName);
                treatmentList.add(treatmentNameAndId);
                categoryAndTreatments.put(categoryName, treatmentList);
            }else{
                HashMap<String, Object> treatmentNameAndId = new HashMap<>();
                treatmentNameAndId.put("treatmentName", treatment.getServiceName());
                treatmentNameAndId.put("treatmentId", treatment.getServiceID());

                List<HashMap<String, Object>> treatmentList = new ArrayList<>();
                treatmentList.add(treatmentNameAndId);
                categoryAndTreatments.put(categoryName, treatmentList);
            }
        }

        return categoryAndTreatments;
    }

    @Override
    public Treatment findById(int id) {
        Optional<Treatment> treatment = this.treatmentRepository.findById(id);
        if (!treatment.isPresent()) {
            throw new RuntimeException("treatment with id:" + id + "not found");
        }
        return treatment.get();
    }

    @Override
    public void deleteById(int id) {
        this.treatmentRepository.deleteById(id);
    }

    @Override
    public void save(Treatment treatment) {
        this.treatmentRepository.save(treatment);
    }

    @Override
    public List<Treatment> findAllByCategoryId(int categoryId) {
        return treatmentRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Integer getTotalTreatmentsDoneEmployee(int id) {
        return treatmentRepository.getTotalTreatmentsDoneEmployee(id);
    }

    @Override
    public Integer countByEmployeeByMonth(int id, LocalDate firstDayOfMonthDate, LocalDate lastDayOfMonthDate) {
        return treatmentRepository.countByEmployeeByMonth(id, firstDayOfMonthDate, lastDayOfMonthDate);
    }

    @Override
    public Map<Integer, Integer> getAmountSingleTreatmentDoneEmployee() {
        return treatmentRepository.getAmountSingleTreatmentDoneEmployee();
    }

}
