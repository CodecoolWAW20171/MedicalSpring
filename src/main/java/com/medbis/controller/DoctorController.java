package com.medbis.controller;

import com.medbis.entity.Doctor;
import com.medbis.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class DoctorController {

    private DoctorServiceImpl doctorService;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public String findAll(Model theModel) {
        theModel.addAttribute("doctors", doctorService.findAll());
    return "doctors/doctor-list";
    }

    @GetMapping("/doctors/showFormForAddDoctor")
    public String showFromForAddDoctor(Model model, Doctor doctor) {
        model.addAttribute("doctor", doctor);
        return "doctors/doctor-form";
    }

    @GetMapping("/doctors/showFormForEditDoctor")
    public String showFormForEditDoctor(@RequestParam("doctorIdToEdit") int theId,
                                        Model theModel) {
        Doctor doctor = doctorService.findById(theId);
        theModel.addAttribute("doctor", doctor);
        return "doctors/doctor-form";
    }

    @PostMapping("/doctors/addNewDoctor")
    public String addNewDoctor(
            @Valid @ModelAttribute("doctor") Doctor doctor,
            BindingResult result) {
        if (result.hasErrors()) {
            return "doctors/doctor-form";
        }
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/doctors/delete")
    public String delete(@RequestParam("doctorIdToDelete") int theId) {
        doctorService.deleteById(theId);
        return "redirect:/doctors";
    }


    }



