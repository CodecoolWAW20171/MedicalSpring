package com.medbis.service.interfaces;


import com.medbis.entity.Treatment;
import com.medbis.entity.User;
import com.medbis.entity.VisitTreatment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public interface AnalysisService {

    int countVisitsByVisitStatusAndVisitDateBetween(boolean visitStatus, int month);

    int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitStatus);

    int getAmountOfVisitsByEmployee(int id, boolean visitsStatus, int month);

    int countVisitsMonthlyByEmployeeIdAndVisitStatus(int month, int employeeId, boolean visitStatus);

    int getAmountOfVisitsByEmployee(int id, boolean visitsStatus);


    Map<Integer, Integer> countByEmployeeByMonth(List<? extends User> users, int month);

  Map<Integer, Integer> createEmployeesResultMapByMonth(boolean visitStatus, List<? extends User> employees, int month);



    int getTotalTreatmentsDoneEmployee(int id);

    List<Treatment> getTreatments();


    int sumVisitsDone();

    int sumVisitsPlanned();


     LinkedHashMap<Treatment, Integer> sortTreatmentsCounterMap(Map<Treatment, Integer> totalTreatmentsDone);

    int totalService(int id);

    Map<Treatment, Integer> createTreatmentsCounterMap(List<Treatment> treatments);



 void countSingleTreatmentPerEmployee(List<VisitTreatment> visitTreatments);

 void generateMap();

   int countVisitTreatmentsDone();


    List<VisitTreatment> getAllVisitTreatment();


    List<VisitTreatment> getVisitTreatmentsDoneInMonth(int month);

    Map<Treatment, Integer> takeSumOfOneTreatmentDoneInMonth(int month);

    LocalDate getLastDayOfMonth(int month);
}


