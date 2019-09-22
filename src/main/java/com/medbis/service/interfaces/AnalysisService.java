package com.medbis.service.interfaces;


import com.medbis.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AnalysisService {



    int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitStatus);

    int countVisitsMonthlyByEmployeeIdAndVisitStatus(int month, int employeeId, boolean visitStatus);

    int getAmountOfVisitsByEmployee(int id, boolean visitsStatus);

    Map<Integer, Integer> createEmployeesResultMap(boolean visitsStatus, List<? extends User> employees);

    Map<Integer, Integer> createEmployeesResultMapByMonth(boolean visitsStatus, List<? extends User> employees, int month);

/*
  musze wywoolywac tua metode poprzez service polaczony z repository tzn = ta sama nazwa ;
*/

    int countStats(int id);

}
