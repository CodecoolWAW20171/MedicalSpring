package com.medbis.service.impl;

import com.medbis.entity.Visit;
import com.medbis.entity.VisitTreatment;
import com.medbis.repository.VisitTreatmentRepository;
import com.medbis.service.interfaces.VisitTreatmentService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Service
public class VisitTreatmentServiceImpl implements VisitTreatmentService {

    private VisitTreatmentRepository visitTreatmentRepository;

    public VisitTreatmentServiceImpl(VisitTreatmentRepository visitTreatmentRepository) {
        this.visitTreatmentRepository = visitTreatmentRepository;
    }

    @Override
    public List<VisitTreatment> findAll() {
        return this.visitTreatmentRepository.findAll();
    }


    @Override
    public List<Integer> findAllByVisitId(Visit visit) {
        try {
           visitTreatmentRepository.findAll();
        }
        catch (NullPointerException err){
            err.printStackTrace();
        }
        return null;
    }

    public int totalService(int id){
        return visitTreatmentRepository.totalService(id);
    }

    @Override
    public List<VisitTreatment> retrieveVisitTreatmentsDistinctly() {
        return  visitTreatmentRepository.retriveVisitTreatmentDistinctly();
    }

    @Override
    public int countVisitTreatmentsDone() {
        return (int) visitTreatmentRepository.count();
    }

    @Override
    public List<VisitTreatment> getVisitTreatmentsDoneInMonth(LocalDate  startDate, LocalDate endDate) {
        return visitTreatmentRepository.takeVisitTreatmentsDoneInMonth(startDate, endDate);
    }

    @Override
    public int takeSumOfOneTreatmentDoneInMonth(int id, LocalDate startDate, LocalDate endDate) {
        return visitTreatmentRepository.takeSumOfOneTreatmentDoneInMonth(id, startDate, endDate);
    }


}
