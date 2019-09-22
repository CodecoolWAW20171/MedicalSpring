package com.medbis.controller;

import com.medbis.entity.Employee;
import com.medbis.entity.User;
import com.medbis.service.interfaces.AnalysisService;
import com.medbis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AnalysisController {

    private UserService userService;
    private AnalysisService analysisService;


    public AnalysisController(@Qualifier("EmployeeServiceImpl") UserService userService, AnalysisService analysisService) {
        this.userService = userService;
        this.analysisService = analysisService;

    }

    @GetMapping("/analysis")
    public String getGeneralStats(Model model) {
        Map<Integer, Integer> visitsDoneByEmployees = new LinkedHashMap<>();
        Map<Integer, Integer> visitsPlannedByEmployees = new LinkedHashMap<>();
        Map<Integer, Integer> treatmentsByEmployees = new LinkedHashMap<>();

        List<? extends User> employees = userService.findAll();

        int amountOfVisitsPlannedByEmployee;
        int amountOfVisitsDoneByEmployee;
        int amountOfTreatmentsByEmployee;
        int sumOfVisitsDone;
        int sumOfVisitsPlanned;
        int sumOfTreatmentsDone;
        int currentId;
        sumOfVisitsDone = 0;
        sumOfVisitsPlanned = 0;
        sumOfTreatmentsDone = 0;
        amountOfTreatmentsByEmployee = 0;


        for (int i = 0; i < employees.size(); i++) {
            Employee employee = (Employee) employees.get(i);
            currentId = employee.getId();

            amountOfVisitsDoneByEmployee = analysisService.getAmountOfVisitsByEmployee(currentId, true);
            amountOfVisitsPlannedByEmployee = analysisService.getAmountOfVisitsByEmployee(currentId, false);
            amountOfTreatmentsByEmployee  = analysisService.countStats(currentId);

            visitsDoneByEmployees.put(i,amountOfVisitsDoneByEmployee);
            visitsPlannedByEmployees.put(i, amountOfVisitsPlannedByEmployee);
            treatmentsByEmployees.put(i, amountOfTreatmentsByEmployee);

            sumOfVisitsDone += amountOfVisitsDoneByEmployee;
            sumOfVisitsPlanned += amountOfVisitsPlannedByEmployee;
            sumOfTreatmentsDone += amountOfTreatmentsByEmployee;
        }

        model.addAttribute("employees", employees);

        model.addAttribute("employeesVisitsDoneMap", visitsDoneByEmployees);
        model.addAttribute("employeesVisitsPlannedMap", visitsPlannedByEmployees);
        model.addAttribute("treatmentsByEmployees", treatmentsByEmployees);

        model.addAttribute("sumOfPlannedVisits", sumOfVisitsPlanned);
        model.addAttribute("sumOfDoneVisits", sumOfVisitsDone);
        model.addAttribute("sumOfTreatmentsByEmployee", sumOfTreatmentsDone);

        return "analysis/analysis";
    }




    @GetMapping("/analysis/details")
    public String getStatsByMonth(Model model, @RequestParam("month") int month){
        List<? extends User> employees = userService.findAll();
        Map<Integer, Integer> visitsDoneMonthlyByEmployees = analysisService.createEmployeesResultMapByMonth(true, employees, month);
        Map<Integer, Integer> visitsPlannedMonthlyByEmployees = analysisService.createEmployeesResultMapByMonth(false, employees, month);
/*
        model.addAttribute("sumOfPlannedVisits", analysisService.getAmountOfVisitsByEmployee(visitsPlannedMonthlyByEmployees));
        model.addAttribute("sumOfDoneVisits", analysisService.getAmountOfVisitsByEmployee(visitsDoneMonthlyByEmployees));*/
        model.addAttribute("employeesVisitsDoneMap", visitsDoneMonthlyByEmployees);
        model.addAttribute("employeesVisitsPlannedMap", visitsPlannedMonthlyByEmployees);
        model.addAttribute("employees", employees);
        model.addAttribute("month", month);

        return "analysis/analysis";
    }




}
