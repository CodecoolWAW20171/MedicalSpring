package com.medbis.service.impl;

import com.medbis.entity.*;
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
    private CategoryServiceImpl categoryService;
    private VisitTreatmentServiceImpl visitTreatmentService;

    public AnalysisServiceImpl(VisitRepository visitRepository, TreatmentServiceImpl treatmentService, CategoryServiceImpl categoryService, VisitTreatmentServiceImpl visitTreatmentService) {
        this.visitRepository = visitRepository;
        this.treatmentService = treatmentService;
        this.categoryService = categoryService;
        this.visitTreatmentService = visitTreatmentService;
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
        LocalDate firstDayOfMonth, lastDayOfMonth;
        firstDayOfMonth = LocalDate.of(2019, month, 1);
        lastDayOfMonth = LocalDate.of(2019, month, Month.of(month).length(isLeapYear(2019)));
        return visitRepository.countVisitsByVisitDateBetweenAndVisitStatusAndEmployeeId(firstDayOfMonth, lastDayOfMonth, visitStatus, employeeId);
    }

    private static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    @Override
    public Map<Integer, Integer> createEmployeesResultMapByMonth(boolean visitsStatus, List<? extends User> employees, int month) {
        Map<Integer, Integer> monthlyResultMap = new LinkedHashMap<>();
        int iterMapKey = 0;

        for (User employee : employees) {
            Employee emp = (Employee) employee;
            monthlyResultMap.put(iterMapKey, countVisitsMonthlyByEmployeeIdAndVisitStatus(month, emp.getId(), visitsStatus));
            iterMapKey++;
        }
        return monthlyResultMap;

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

        LocalDate firstDayOfMonth, lastDayOfMonth;
        firstDayOfMonth = LocalDate.of(2019, month, 1);
        lastDayOfMonth = LocalDate.of(2019, month, Month.of(month).length(isLeapYear(2019)));

        int result = 0;
        for (int i = 0; i < users.size(); i++) {
            employee = (Employee) users.get(i);
            try {
                result = treatmentService.countByEmployeeByMonth(employee.getId(), firstDayOfMonth, lastDayOfMonth);
            } catch (NullPointerException err) {
                result = 0;
            }
            treatmentsByEmployees.put(i, result);
        }

        return treatmentsByEmployees;
    }


    @Override
    public List<Category> getCategories() {
        return categoryService.findAll();
    }
/*
  @Override
  public Map<Integer, List<Treatment>> getTreatmentsByCategoryIdMap() {
        Map<Integer, List<Treatment>> treatmentsByCategoryId = new HashMap<>();
        List<Category> categories = categoryService.findAll();
        List<Treatment> treatments = treatmentService.findAll();

        List<Treatment> tById = new ArrayList<>();

        for (Treatment treatment : treatments) {
            for (Category category : categories) {
                treatmentsByCategoryId.put(category.getCategoryId(), tById);
                if (category.getCategoryId() == treatment.getCategoryId()) {
                    tById.add(treatment);
                    treatmentsByCategoryId.put(category.getCategoryId(), tById);
                }
            }
        }
        return treatmentsByCategoryId;
    }*/



    @Override
    public int sumVisitsDone() {
        return this.visitRepository.countVisitsByVisitStatus(true);
    }

    @Override
    public int sumVisitsPlanned() {
        return this.visitRepository.countVisitsByVisitStatus(false);
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


/*  @Override
    public void countSingleTreatmentPerEmployee(List<? extends User> employees) {
        int result;
        List<VisitTreatment> visitTreatments = visitTreatmentService.findAll();
        for (User emp : employees) {
            Employee employee = (Employee) emp;
            employee.setCountTreat(generateMap());
            for (VisitTreatment visitTreatment : visitTreatments) {
                Visit visit = visitTreatment.getVisit();
                result = 0;
              if (visit.getVisitStatus()) {
                    if (visit.getEmployee().equals(employee)) {
                        List<VisitTreatment> vs = visit.getVisitTreatments();
                            for (VisitTreatment v : vs){
                                if (v.equals(visitTreatment)){
                                    result+=1;
                                }
                            }
                        employee.addTreatmentsCount(visitTreatment.getTreatment(), result);
                    }
                }
            }
        }
    }*/


/*    @Override
    public Map<Treatment, Integer> generateMap() {
        List<Treatment> treatments = treatmentService.findAll();
        Map<Treatment, Integer> treatmentIntegerMap = new HashMap<>();
        for (Treatment treatment : treatments) {
            treatmentIntegerMap.put(treatment, 0);
        }
        return treatmentIntegerMap;
    }*/

    @Override
    public int countVisitTreatmentsDone() {
        return visitTreatmentService.countVisitTreatmentsDone();
    }


}


