package com.medbis.controller;

import com.medbis.entity.Employee;
import com.medbis.entity.Treatment;
import com.medbis.entity.User;
import com.medbis.entity.VisitTreatment;
import com.medbis.service.interfaces.AnalysisService;
import com.medbis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Month;
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
        List<Treatment> treatments = analysisService.getTreatments();
        List<VisitTreatment> visitTreatments = analysisService.getAllVisitTreatment();
        int currentId;
        int sumOfTreatmentsDone = analysisService.countVisitTreatmentsDone();
        LinkedHashMap<Treatment, Integer> sortedTreatmentsTotalResultMap = analysisService.sortTreatmentsCounterMap(analysisService.createTreatmentsCounterMap(treatments));


        for (int i = 0; i < employees.size(); i++) {
            Employee emp = (Employee) employees.get(i);
            currentId = emp.getId();
            visitsDoneByEmployees.put(i, analysisService.getAmountOfVisitsByEmployee(currentId, true));
            visitsPlannedByEmployees.put(i, analysisService.getAmountOfVisitsByEmployee(currentId, false));
            treatmentsByEmployees.put(i, analysisService.getTotalTreatmentsDoneEmployee(currentId));
        }

        analysisService.generateMap();

        analysisService.countSingleTreatmentPerEmployee(visitTreatments);

        model.addAttribute("totalTreatmentsDone", sortedTreatmentsTotalResultMap);

        model.addAttribute("employees", employees);
        model.addAttribute("treatments", treatments);

        model.addAttribute("employeesVisitsDoneMap", visitsDoneByEmployees);
        model.addAttribute("employeesVisitsPlannedMap", visitsPlannedByEmployees);
        model.addAttribute("treatmentsByEmployees", treatmentsByEmployees);

        model.addAttribute("sumOfPlannedVisits", analysisService.sumVisitsPlanned());
        model.addAttribute("sumOfDoneVisits", analysisService.sumVisitsDone());

        model.addAttribute("sumOfTreatmentsByEmployee", sumOfTreatmentsDone);

        return "analysis/analysis";
    }


    @GetMapping("/analysis/details")
    public String getStatsByMonth(Model model, @RequestParam("month") int month){

        List<? extends User> employees = userService.findAll();

        Map<Integer, Integer> visitsPlannedMonthlyByEmployees = analysisService.createEmployeesResultMapByMonth(false, employees, month);
        Map<Integer, Integer> visitsDoneMonthlyByEmployees = analysisService.createEmployeesResultMapByMonth(true, employees, month);
        Map<Integer, Integer> treatmentsByEmployees = analysisService.countByEmployeeByMonth(employees, month);

        List<Treatment> treatments = analysisService.getTreatments();

       int sumOfTreatmentsDone = 0;

        analysisService.generateMap();

        try {
            List<VisitTreatment> visitTreatmentsDoneInMonth = analysisService.getVisitTreatmentsDoneInMonth(month);
            sumOfTreatmentsDone = visitTreatmentsDoneInMonth.size();
            analysisService.countSingleTreatmentPerEmployee(visitTreatmentsDoneInMonth);
        }
        catch (NullPointerException err){
           err.printStackTrace();
        }

        model.addAttribute("sumOfOneTreatmentByMonth", analysisService.takeSumOfOneTreatmentDoneInMonth(month));

        model.addAttribute("totalTreatmentsDone", analysisService.sortTreatmentsCounterMap(analysisService.createTreatmentsCounterMap(treatments)));
        model.addAttribute("sumOfPlannedVisits", analysisService.countVisitsByVisitStatusAndVisitDateBetween(false, month));
        model.addAttribute("sumOfDoneVisits", analysisService.countVisitsByVisitStatusAndVisitDateBetween(true, month));

        model.addAttribute("treatmentsByEmployees", treatmentsByEmployees);
        model.addAttribute("treatments", treatments);
        model.addAttribute("employeesVisitsDoneMap", visitsDoneMonthlyByEmployees);
        model.addAttribute("employeesVisitsPlannedMap", visitsPlannedMonthlyByEmployees);
        model.addAttribute("employees", employees);
        model.addAttribute("month", Month.of(month));
        model.addAttribute("sumOfTreatmentsByEmployee", sumOfTreatmentsDone);
        return "analysis/analysis";
    }
}
