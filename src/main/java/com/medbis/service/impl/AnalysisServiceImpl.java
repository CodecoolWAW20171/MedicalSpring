package com.medbis.service.impl;

import com.medbis.entity.Employee;
import com.medbis.entity.User;
import com.medbis.entity.Visit;
import com.medbis.repository.VisitRepository;
import com.medbis.service.interfaces.AnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private VisitRepository visitRepository;
    private TreatmentServiceImpl treatmentService;
    private EmployeeServiceImpl employeeService;

    public AnalysisServiceImpl(VisitRepository visitRepository, TreatmentServiceImpl treatmentService, EmployeeServiceImpl employeeService) {
        this.visitRepository = visitRepository;
        this.treatmentService = treatmentService;
        this.employeeService = employeeService;
    }

    @Override
    public int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitStatus) {
        return visitRepository.countVisitsByEmployeeIdAndVisitStatus(id, visitStatus);
    }

    @Override
   public int getAmountOfVisitsByEmployee(int id, boolean visitsStatus  ) {
       return countVisitsByEmployeeIdAndVisitStatus(id, visitsStatus);

   }

    @Override
    public Map<Integer, Integer> createEmployeesResultMap(boolean visitsStatus, List<? extends User> employees) {
        Map<Integer, Integer> resultMap = new LinkedHashMap<>();
        int iterMapKey = 0;

        for (User employee : employees) {
            Employee emp = (Employee) employee;
            resultMap.put(iterMapKey, countVisitsByEmployeeIdAndVisitStatus(emp.getId(), visitsStatus));
            iterMapKey++;
        }
        return resultMap;
    }

    @Override
    public int countVisitsMonthlyByEmployeeIdAndVisitStatus(int month, int employeeId, boolean visitStatus) {
        LocalDate firstDayOfMonth, lastDayOfMonth;
        firstDayOfMonth = LocalDate.of(2019, month, 1);
        lastDayOfMonth = LocalDate.of(2019, month, Month.of(month).length(isLeapYear(2019)));
        return visitRepository.countVisitsByVisitDateBetweenAndVisitStatusAndEmployeeId(firstDayOfMonth, lastDayOfMonth, visitStatus, employeeId);
    }

    @Override
    public Map<Integer, Integer> createEmployeesResultMapByMonth(boolean visitsStatus, List<? extends User> employees, int month) {
        Map<Integer, Integer> monthlyResultMap = new LinkedHashMap<>();
        int iterMapKey = 0;

        for (User employee : employees) {
            Employee emp = (Employee) employee;
            monthlyResultMap.put(iterMapKey, countVisitsMonthlyByEmployeeIdAndVisitStatus(month ,emp.getId(), visitsStatus));
            iterMapKey++;
        }
        return monthlyResultMap;

    }

    @Override
    public int countStats(int id) {
        int amountOfVisisDoneByEmployee;
        try {
            amountOfVisisDoneByEmployee = treatmentService.countStats(id);
        }
        catch (NullPointerException err){
            amountOfVisisDoneByEmployee = 0;
        }
        return amountOfVisisDoneByEmployee;
    }


    private static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }


    private Map<Integer, List<Visit>> getVisitDoneForEmployeeMap(){
        Map<Integer, List<Visit>> visitsDoneByEmployee = new HashMap<>();
        employeeService.findAll().forEach(employee -> {
            Employee em = (Employee) employee;
            int employeeId = em.getId();
            visitsDoneByEmployee.put(employeeId, visitRepository.findByVisitStatusIsTrueAndEmployeeId(employeeId));
        });
    return visitsDoneByEmployee;
    }




}