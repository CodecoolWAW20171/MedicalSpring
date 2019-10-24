package com.medbis.service.interfaces;

import com.medbis.entity.Visit;
import com.medbis.entity.VisitTreatment;

import java.time.LocalDate;
import java.util.List;

public interface VisitTreatmentService {

    List<VisitTreatment> findAll();

    List<Integer> findAllByVisitId(Visit visit);

    int totalService(int id);

    List<VisitTreatment> retrieveVisitTreatmentsDistinctly();

    int countVisitTreatmentsDone();

    List<VisitTreatment> getVisitTreatmentsDoneInMonth(LocalDate startDate, LocalDate endDate);

}
