package com.medbis.statistics;

import com.medbis.entity.Employee;
import com.medbis.entity.User;
import com.medbis.repository.VisitRepository;
import com.medbis.service.impl.AnalysisServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
public class VisitsCounter {

    private VisitRepository visitRepository;


    public VisitsCounter(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }


    public int countVisitsMonthlyByEmployeeIdAndVisitStatus(int month, int employeeId, boolean visitStatus) {
        LocalDate firstDayOfMonth, lastDayOfMonth;

        /*
        todo: handle form input for year to unhardcode this
        */
        firstDayOfMonth = LocalDate.of(2019, month, 1);
        lastDayOfMonth = LocalDate.of(2019, month, Month.of(month).length(AnalysisServiceImpl.isLeapYear(2019)));
        return visitRepository.countVisitsByVisitDateBetweenAndVisitStatusAndEmployeeId(firstDayOfMonth, lastDayOfMonth, visitStatus, employeeId);
    }

    public Map<Integer, Integer> createEmployeesResultMapByMonth(boolean visitStatus, List<? extends User> employees, int month) {
        Map<Integer, Integer> monthlyResultMap = new LinkedHashMap<>();
        int iterMapKey = 0;

        for (User employee : employees) {
            Employee emp = (Employee) employee;
            monthlyResultMap.put(iterMapKey, countVisitsMonthlyByEmployeeIdAndVisitStatus(month, emp.getId(), visitStatus));
            iterMapKey++;
        }
        return monthlyResultMap;
    }

    public int countVisitsByVisitStatus(boolean visitStatus){
            return visitRepository.countVisitsByVisitStatus(visitStatus);
    }

    public int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitStatus){
        return visitRepository.countVisitsByEmployeeIdAndVisitStatus(id, visitStatus);
    }

}









