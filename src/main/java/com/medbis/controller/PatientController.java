package com.medbis.controller;

import com.medbis.entity.Disease;
import com.medbis.entity.Doctor;
import com.medbis.entity.Medicine;
import com.medbis.entity.Patient;
import com.medbis.factory.UserFactory;
import com.medbis.service.impl.DoctorServiceImpl;
import com.medbis.service.interfaces.DiseaseService;
import com.medbis.service.interfaces.DoctorService;
import com.medbis.service.interfaces.MedicineService;
import com.medbis.service.interfaces.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PatientController {

    private MedicineService medicineService;
    private DiseaseService diseaseService;
    private UserService userService;
    private UserFactory userFactory;
    private DoctorServiceImpl doctorService;

    @Autowired
    public PatientController(@Qualifier(value = "PatientServiceImpl") UserService userService, UserFactory userFactory, DiseaseService diseaseService, MedicineService medicineService, DoctorServiceImpl doctorService) {
        this.userService = userService;
        this.userFactory = userFactory;
        this.diseaseService = diseaseService;
        this.medicineService = medicineService;
        this.doctorService = doctorService;
    }


    @RequestMapping(value = {"/patients", "", "/"})
    public String findAll(Model theModel){
        theModel.addAttribute("patientList", userService.findAll());
        return "users/patient-list";
    }

    //Show form for ADD NEW PATIENT
    @GetMapping("/patients/showFormForAddPatient")
    public String showFormForAddPatient(Model theModel){
//        theModel.addAttribute("diseases", diseaseService.findAll());
        Patient newPatient = (Patient) userFactory.getNewUser("patient");
        theModel.addAttribute("patient",newPatient);
        theModel.addAttribute("allMedicines", medicineService.findAll());
        theModel.addAttribute("allDiseases", diseaseService.findAll());
        theModel.addAttribute("doctors", doctorService.findAll());
        return "users/patient-form";
    }

    /* MEDICINES ***************************************/
    //Add NEW ROW FOR MEDICINE, look params!
    @PostMapping(value="/patients/addNewPatient", params={"addRow"})
    public String addMedicineRow(Model theModel, @ModelAttribute("patient") Patient thePatient) {
        thePatient.getPatientMedicines().add(new Medicine()); //.getRows().add(new Row());
        theModel.addAttribute("allMedicines", medicineService.findAll());
        theModel.addAttribute("allDiseases", diseaseService.findAll());
        return "users/patient-form";
    }
    //DELETE ONE ROW OF MEDICINE, look params!
    @PostMapping(value="/patients/addNewPatient", params={"removeRow"})
    public String delMedicineRow(Model theModel, @ModelAttribute("patient") Patient thePatient, final HttpServletRequest req) {
        theModel.addAttribute("allMedicines", medicineService.findAll());
        theModel.addAttribute("allDiseases", diseaseService.findAll());
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        thePatient.getPatientMedicines().remove(rowId.intValue());
        return "users/patient-form";
    }



    /* DISEASE ***************************************/
    //Add NEW ROW FOR DISEASE, look params!
    @PostMapping(value="/patients/addNewPatient", params={"addDiseaseRow"})
    public String addDiseaseRow(Model theModel, @ModelAttribute("patient") Patient thePatient) {
        thePatient.getPatientDiseases().add(new Disease()); //.getRows().add(new Row());
        theModel.addAttribute("allMedicines", medicineService.findAll());
        theModel.addAttribute("allDiseases", diseaseService.findAll());
        return "users/patient-form";
    }
    //DELETE ONE ROW OF DISEASE, look params!
    @PostMapping(value="/patients/addNewPatient", params={"removeDiseaseRow"})
    public String delDiseaseRow(Model theModel, @ModelAttribute("patient") Patient thePatient, final HttpServletRequest req) {
        theModel.addAttribute("allMedicines", medicineService.findAll());
        theModel.addAttribute("allDiseases", diseaseService.findAll());
        final Integer rowId = Integer.valueOf(req.getParameter("removeDiseaseRow"));
        thePatient.getPatientDiseases().remove(rowId.intValue());
        return "users/patient-form";
    }







    //ADD NEW PATIENT
    @PostMapping("/patients/addNewPatient")
    public String addNewPatient(Model theModel, @Valid @ModelAttribute("patient") Patient thePatient, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            theModel.addAttribute("allMedicines", medicineService.findAll());
            theModel.addAttribute("allDiseases", diseaseService.findAll());
            return "users/patient-form";
        }else{
            userService.save(thePatient);
            return "redirect:/patients";
        }
    }

    //EDITING NEW PATIENT
    @GetMapping ("/patients/showFormForEditPatient")
    public String showFormForEditMedicine(@RequestParam("patientIdToEdit")int theId, Model theModel){
        Patient newPatient = (Patient) userService.findById(theId);
        theModel.addAttribute("allMedicines", medicineService.findAll());
        theModel.addAttribute("allDiseases", diseaseService.findAll());
        theModel.addAttribute("patient", newPatient);
        return "users/patient-form";
    }

    //DELETING NEW PATIENT
    @GetMapping("/patients/delete")
    public String delete(@RequestParam("patientIdToDelete")int theId){
        userService.deleteById(theId);
        return "redirect:/patients";
    }

    //Show patient details, all
    @GetMapping ("/patients/showPatientDetails")
    public String showPatientDetails(@RequestParam("patientIdDetails")int theId, Model theModel){
        Patient newPatient = (Patient) userService.findById(theId);
        Integer doctorId = newPatient.getDoctorId();
        Doctor doctor = doctorService.findById(doctorId);
        theModel.addAttribute("patient", newPatient);
        theModel.addAttribute("doctor", doctor);
        return "users/patient-details";
    }





}
