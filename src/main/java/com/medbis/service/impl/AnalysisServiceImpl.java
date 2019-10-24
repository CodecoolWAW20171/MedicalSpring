package com.medbis.service.impl;

import com.medbis.entity.*;
import com.medbis.repository.VisitRepository;
import com.medbis.service.interfaces.AnalysisService;
import com.medbis.statistics.VisitsCounter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private VisitRepository visitRepository;
    private TreatmentServiceImpl treatmentService;
    private CategoryServiceImpl categoryService;
    private VisitTreatmentServiceImpl visitTreatmentService;
    private VisitServiceImpl visitService;
    private EmployeeServiceImpl employeeService;
    private VisitsCounter visitsCounter;

    public AnalysisServiceImpl(VisitRepository visitRepository, TreatmentServiceImpl treatmentService, CategoryServiceImpl categoryService, VisitTreatmentServiceImpl visitTreatmentService, VisitServiceImpl visitService, EmployeeServiceImpl employeeService, VisitsCounter visitsCounter) {
        this.visitRepository = visitRepository;
        this.treatmentService = treatmentService;
        this.categoryService = categoryService;
        this.visitTreatmentService = visitTreatmentService;
        this.visitService = visitService;
        this.employeeService = employeeService;
        this.visitsCounter = visitsCounter;
    }

    @Override
    public int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitStatus) {
        return visitRepository.countVisitsByEmployeeIdAndVisitStatus(id, visitStatus);
    }

    @Override
    public int getAmountOfVisitsByEmployee(int id, boolean visitsStatus) {
        return countVisitsByEmployeeIdAndVisitStatus(id, visitsStatus);

    }

    @Override
    public int getAmountOfVisitsByEmployee(int id, boolean visitsStatus, int month) {
        return countVisitsMonthlyByEmployeeIdAndVisitStatus(month, id, visitsStatus);
    }



/*
    private int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitsStatus, int month) {
        visitRepository.countVisitsByVisitDateBetweenAndVisitStatusAndEmployeeId(firstDayOfMonth, lastDayOfMonth, visitStatus, employeeId);
    }
*/


    @Override
    public int countVisitsMonthlyByEmployeeIdAndVisitStatus(int month, int employeeId, boolean visitStatus) {
        return visitsCounter.countVisitsMonthlyByEmployeeIdAndVisitStatus(month, employeeId, visitStatus);
    }


    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    @Override
    public Map<Integer, Integer> createEmployeesResultMapByMonth(boolean visitStatus, List<? extends User> employees, int month) {
        return visitsCounter.createEmployeesResultMapByMonth(visitStatus, employees, month);

        /*
        Map<Integer, Integer> monthlyResultMap = new LinkedHashMap<>();
        int iterMapKey = 0;

        for (User employee : employees) {
            Employee emp = (Employee) employee;
            monthlyResultMap.put(iterMapKey, countVisitsMonthlyByEmployeeIdAndVisitStatus(month, emp.getId(), visitStatus));
            iterMapKey++;
        }
        return monthlyResultMap;
*/

    }


    @Override
    public int countStats(int id) {
        int amountOfVisisDoneByEmployee;
        try {
            amountOfVisisDoneByEmployee = treatmentService.countStats(id);
        } catch (NullPointerException err) {
            amountOfVisisDoneByEmployee = 0;
        }
        return amountOfVisisDoneByEmployee;
    }

    @Override
    public List<Treatment> getTreatments() {
        return treatmentService.findAll();
    }


    public Map<Integer, Integer> countByEmployeeByMonth(List<? extends User> users, int month) {
        Map<Integer, Integer> treatmentsByEmployees = new LinkedHashMap<>();
        Employee employee;

        LocalDate lastDayOfMonth = getLastDayOfMonth(month);

        int result;
        for (int i = 0; i < users.size(); i++) {
            try {
                employee = (Employee) users.get(i);
                result = treatmentService.countByEmployeeByMonth(employee.getId(), LocalDate.of(2019, month, 1), lastDayOfMonth);
            } catch (NullPointerException err) {
                result = 0;
            }
            treatmentsByEmployees.put(i, result);
        }
        return treatmentsByEmployees;
    }

   public LocalDate getLastDayOfMonth(int month) {
        return LocalDate.of(2019, month, Month.of(month).length(isLeapYear(2019)));
    }


    @Override
    public List<Category> getCategories() {
        return categoryService.findAll();
    }


    @Override
    public int sumVisitsDone() {
        return this.visitsCounter.countVisitsByVisitStatus(true);
    }

    @Override
    public int sumVisitsPlanned() {
        return this.visitsCounter.countVisitsByVisitStatus(false);
    }

/*    @Override
    public Map<Integer, Integer> getAmountSingleTreatmentDoneEmployee() {
        return this.treatmentService.getAmountSingleTreatmentDoneEmployee();
    }*/

    @Override
    public int totalService(int id) {
        return this.visitTreatmentService.totalService(id);
    }


     public Map<Treatment, Integer> createTreatmentsCounterMap(List<Treatment> treatments) {
        Map<Treatment, Integer> totalTreatmentsDone = new HashMap<>();
        int sum, treatmentId;
        for (Treatment treatment : treatments) {
            treatmentId = treatment.getTreatmentId();
            sum = totalService(treatmentId);
            totalTreatmentsDone.put(treatment, sum);
        }
        return totalTreatmentsDone;
    }

   public LinkedHashMap<Treatment, Integer> sortTreatmentsCounterMap(Map<Treatment, Integer> totalTreatmentsDone) {
        List<Map.Entry<Treatment, Integer>> entries =
                new LinkedList<>(totalTreatmentsDone.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<Treatment, Integer>>() {
            @Override
            public int compare(Map.Entry<Treatment, Integer> o1,
                               Map.Entry<Treatment, Integer> o2) {
                Integer st = o1.getKey().getTreatmentId();
                Integer nd = o2.getKey().getTreatmentId();
                return st.compareTo(nd);
            }
        });
        LinkedHashMap<Treatment, Integer> srt = new LinkedHashMap<>();
        for (Map.Entry<Treatment, Integer> entry : entries) {
            srt.put(entry.getKey(), entry.getValue());
        }
        return srt;
    }


    @Override
    public void countSingleTreatmentPerEmployee(List<VisitTreatment> visitTreatments) {
        int visitId;
        Visit visit;
        Employee emp;
        Treatment treatment;

        for(VisitTreatment visitTreatment : visitTreatments) {
            try {
                visitId = visitTreatment.getPrimaryKey().getVisit().getVisitId();
                visit = visitService.findById(visitId);
                emp = (Employee) employeeService.findById(visit.getVisitEmployeeId());
                treatment = treatmentService.findById(visitTreatment.getTreatment().getTreatmentId());
                emp.raiseResultOfTreatmentDone(treatment);
                }
              catch (NullPointerException err){
                //just want to take next elem
                  continue;
              }
        }
    }



    @Override
    public void generateMap() {
        List<Treatment> treatments = treatmentService.findAll();
        List<? extends User> employees = employeeService.findAll();
        Employee emp;
        for(User employee : employees){
            emp = (Employee) employee;
            emp.generateMapOfTreatments(treatments);
        }
    }

    @Override
    public int countVisitTreatmentsDone() {
        return visitTreatmentService.countVisitTreatmentsDone();
    }

    @Override
    public List<VisitTreatment> getAllVisitTreatment() {
        return visitTreatmentService.findAll();
    }

    @Override
    public List<VisitTreatment> getVisitTreatmentsDoneInMonth(LocalDate startDate, LocalDate endDate) {
        return visitTreatmentService.getVisitTreatmentsDoneInMonth(startDate, endDate);
    }


}


