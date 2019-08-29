package com.medbis.service.interfaces;


import com.medbis.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AnalysisService {



    int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitStatus);

    int countVisitsMonthlyByEmployeeIdAndVisitStatus(int month, int employeeId, boolean visitStatus);

    int getSumOfVisits(Map<Integer, Integer> visitsPlannedByEmployees);

    Map<Integer, Integer> createEmployeesResultMap(boolean visitsStatus, List<? extends User> employees);

    Map<Integer, Integer> createEmployeesResultMapByMonth(boolean visitsStatus, List<? extends User> employees, int month);
}
