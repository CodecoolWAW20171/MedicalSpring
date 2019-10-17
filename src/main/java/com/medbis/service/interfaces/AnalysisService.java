package com.medbis.service.interfaces;


import com.medbis.entity.Category;
import com.medbis.entity.Treatment;
import com.medbis.entity.User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public interface AnalysisService {



    int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitStatus);

    int getAmountOfVisitsByEmployee(int id, boolean visitsStatus, int month);

    int countVisitsMonthlyByEmployeeIdAndVisitStatus(int month, int employeeId, boolean visitStatus);

    int getAmountOfVisitsByEmployee(int id, boolean visitsStatus);


    Map<Integer, Integer> countByEmployeeByMonth(List<? extends User> users, int month);

  Map<Integer, Integer> createEmployeesResultMapByMonth(boolean visitsStatus, List<? extends User> employees, int month);



    List<Category> getCategories();

    int countStats(int id);

    List<Treatment> getTreatments();


    int sumVisitsDone();

    int sumVisitsPlanned();


     LinkedHashMap<Treatment, Integer> sortTreatmentsCounterMap(Map<Treatment, Integer> totalTreatmentsDone);

    int totalService(int id);

    Map<Treatment, Integer> createTreatmentsCounterMap(List<Treatment> treatments);



 //void countSingleTreatmentPerEmployee(List<? extends User> employees);

   //Map<Treatment, Integer> generateMap();

   int countVisitTreatmentsDone();

}


