package com.medbis.controller;

import com.medbis.entity.User;
import com.medbis.service.interfaces.AnalysisService;
import com.medbis.service.interfaces.TreatmentService;
import com.medbis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AnalysisController {

    private UserService userService;
    private AnalysisService analysisService;
    private TreatmentService treatmentService;


    public AnalysisController(@Qualifier("EmployeeServiceImpl") UserService userService, AnalysisService analysisService, TreatmentService treatmentService) {
        this.userService = userService;
        this.analysisService = analysisService;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/analysis")
    public String getGeneralStats(Model model) {
        List<? extends User> employees = userService.findAll();
        Map<Integer, Integer> visitsDoneByEmployees = analysisService.createEmployeesResultMap(true, employees);
        Map<Integer, Integer> visitsPlannedByEmployees = analysisService.createEmployeesResultMap(false, employees);

        model.addAttribute("sumOfPlannedVisits", analysisService.getSumOfVisits(visitsPlannedByEmployees));
        model.addAttribute("sumOfDoneVisits", analysisService.getSumOfVisits(visitsDoneByEmployees));
        model.addAttribute("employeesVisitsDoneMap", visitsDoneByEmployees);
        model.addAttribute("employeesVisitsPlannedMap", visitsPlannedByEmployees);
        model.addAttribute("employees", employees);
        return "analysis/analysis";
    }


    @GetMapping("/analysis/details")
    public String getStatsByMonth(Model model, @RequestParam("month") int month){
        List<? extends User> employees = userService.findAll();
        Map<Integer, Integer> visitsDoneMonthlyByEmployees = analysisService.createEmployeesResultMapByMonth(true, employees, month);
        Map<Integer, Integer> visitsPlannedMonthlyByEmployees = analysisService.createEmployeesResultMapByMonth(false, employees, month);

        model.addAttribute("sumOfPlannedVisits", analysisService.getSumOfVisits(visitsPlannedMonthlyByEmployees));
        model.addAttribute("sumOfDoneVisits", analysisService.getSumOfVisits(visitsDoneMonthlyByEmployees));
        model.addAttribute("employeesVisitsDoneMap", visitsDoneMonthlyByEmployees);
        model.addAttribute("employeesVisitsPlannedMap", visitsPlannedMonthlyByEmployees);
        model.addAttribute("employees", employees);

        return "analysis/analysis";
    }




}
