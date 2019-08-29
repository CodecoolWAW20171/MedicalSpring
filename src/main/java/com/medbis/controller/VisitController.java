package com.medbis.controller;

import com.medbis.entity.*;
import com.medbis.mail.MailService;
import com.medbis.pdf.PdfGenerator;
import com.medbis.security.UserPrincipal;
import com.medbis.service.interfaces.CategoryService;
import com.medbis.service.interfaces.TreatmentService;
import com.medbis.service.interfaces.UserService;
import com.medbis.service.interfaces.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VisitController {

    private VisitService visitService;
    private UserService userService;
    private CategoryService categoryService;
    private TreatmentService treatmentService;
    private MailService mailService;
    private UserService employeeService;


    @Autowired
    public VisitController(VisitService visitService,
                           @Qualifier("PatientServiceImpl") UserService userService,
                           CategoryService categoryService,
                           TreatmentService treatmentService,
                           MailService mailService,
                           @Qualifier("EmployeeServiceImpl") UserService employeeService) {
        this.visitService = visitService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.treatmentService = treatmentService;
        this.mailService = mailService;
        this.employeeService = employeeService;
    }

    @GetMapping("visits/delete")
    public String deleteVisit(@RequestParam("visitId") int visitId) {
        mailService.setAction("deleteVisit");
        mailService.setVisit(visitService.findById(visitId));
        mailService.run();
        this.visitService.deleteById(visitId);
        return "redirect:/visits";
    }



    //Show form for ADD NEW VISIT
    @GetMapping("/visits/showFormForAddVisit")
    public String showFormForAddVisit(@RequestParam("patientId")int thePatientId, Model theModel){
        Patient thePatient = (Patient) userService.findById(thePatientId);
        Visit newVisit = new Visit();

        newVisit.setPatient(thePatient);
        theModel.addAttribute("visit", newVisit);
        theModel.addAttribute("patientId", thePatientId);

        theModel.addAttribute("categories", categoryService.findAll());
        theModel.addAttribute("allTreatments", treatmentService.findAll());
         return "visits/visit-form";
    }

    //ADDING NEW VISITS
    @PostMapping("/visits/{action}/addNewVisit")
    public String addNewVisit(@PathVariable("action") String action, Model theModel, @Valid @ModelAttribute("visit") Visit theVisit, BindingResult bindingResult) {
        Patient thePatient = (Patient) userService.findById(theVisit.getVisitPatientId());
        theVisit.setPatient(thePatient);
        if (bindingResult.hasErrors()) {
            theVisit.setPatient(thePatient);
            theModel.addAttribute("patientId", theVisit.getVisitPatientId());
            theModel.addAttribute("allTreatments", treatmentService.findAll());
            return "visits/visit-form";
        }
        int initialAmountOfPlannedVisit = visitService.findPlannedVisits().size();
        for(VisitTreatment visitTreatment: theVisit.getVisitTreatments()){
            visitTreatment.setVisit(theVisit);
            System.out.println("Saving... "  + Instant.now());
        }
        visitService.save(theVisit);
        System.out.println("Saved... "  + Instant.now());
        if (visitService.checkIfNewVisitAdded(initialAmountOfPlannedVisit)) {
            mailService.setVisit(theVisit);
            mailService.setAction("addVisit");
            Thread thread = new Thread(mailService);
            thread.run();
        }
        else if(action.equals("edit")) {
            mailService.setVisit(theVisit);
            mailService.setAction("editVisit");
            mailService.run();
            System.out.println("Send email... " + Instant.now() );
            mailService.sendMail();
            visitService.save(theVisit);
        }
        if(action.equals("hold")){
            System.out.println("Holding... "  + Instant.now());
            theVisit.setVisitStatus(true);
            visitService.save(theVisit);
        }
        System.out.println("rendering............ "  + Instant.now());
        return "redirect:/visits";
    }


    @GetMapping("visits")
    public String showSplittedList(@RequestParam(value = "status", defaultValue = "all") String isVisitDone, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        switch (isVisitDone) {
            case "all":
                model.addAttribute("visitsList", visitService.findAllByEmployeeId(userDetails.getEmployee().getId()));
                break;
            case "true":
                model.addAttribute("visitsList", visitService.findAccomplishedVisitsByEmployeeId(userDetails.getEmployee().getId()));
                break;
            case "false":
                model.addAttribute("visitsList", visitService.findPlannedVisitsByEmployeeId(userDetails.getEmployee().getId()));
        }
        return "visits/visit-list";
    }

    @GetMapping("/visits/all-visits-list")
    public String showAllVisitsForAdmin(Model model){
        model.addAttribute("visitsList", this.visitService.findAll());
        return "visits/visit-list";
    }

    @GetMapping ("/visits/showFormForEditVisit")
    public String showFormForEditMedicine(@RequestParam("visitIdToEdit")int theId, @RequestParam("action") String action ,Model theModel) {
        Visit visitToEdit = visitService.findById(theId);
        theModel.addAttribute("visit", visitToEdit);
        theModel.addAttribute("patientId", visitToEdit.getVisitPatientId());
        theModel.addAttribute("allTreatments", treatmentService.findAll());

        if(action.equals("hold")) {
            return "visits/visit-hold";
        }
        else {
            return "visits/visit-form";
        }
    }

    @GetMapping("/visits/generate-document")
    public String generatePdfRaport(@RequestParam("visitId") int visitId) {
        Visit reportedVisit = this.visitService.findById(visitId);
        PdfGenerator pdfGenerator = new PdfGenerator(reportedVisit);
        pdfGenerator.createVisitRaport();
        return "redirect:/visits";
    }


    /* TREATMENTS ***************************************/
    //Add NEW ROW FOR TREATMENTS, look params!
    @PostMapping(value="/visits/{action}/addNewVisit", params={"addRow"})
    public String addTreatmentRow(@PathVariable("action") String action, final HttpServletRequest req, Model theModel, @ModelAttribute("visit") Visit theVisit) {

        Patient thePatient = (Patient) userService.findById(theVisit.getVisitPatientId());
        theModel.addAttribute("patientId", theVisit.getVisitPatientId() );
        theVisit.setPatient(thePatient);
        theVisit.getVisitTreatments().add(new VisitTreatment());

        final Integer categoryId = Integer.valueOf(req.getParameter("addRow"));
        theModel.addAttribute("allTreatments", treatmentService.findAll());
        theModel.addAttribute("selectedTreatments", treatmentService.findAllByCategoryId(categoryId));

        if(action.equals("edit")){
            return "visits/visit-form";
        }
        else {
            return "visits/visit-hold";
        }

    }
    //DELETE ONE ROW OF TREATMENTS, look params!
    @PostMapping(value="/visits/{action}/addNewVisit", params={"removeRow"})
    public String delTreatmentRow(Model theModel, @ModelAttribute("visit") Visit theVisit, final HttpServletRequest req, @PathVariable String action) {
        theModel.addAttribute("patientId", theVisit.getVisitPatientId());
        Patient thePatient = (Patient) userService.findById(theVisit.getVisitPatientId());
        theVisit.setPatient(thePatient);
        theModel.addAttribute("allTreatments", treatmentService.findAll());
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        theVisit.getVisitTreatments().remove(rowId.intValue());

    if (action.equals("edit")) {
            return "visits/visit-form";
        } else {
            return "visits/visit-hold";
        }
    }

}
