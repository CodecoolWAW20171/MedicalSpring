package com.medbis.controller;


import com.medbis.entity.Disease;
import com.medbis.service.interfaces.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DiseaseController {

    private DiseaseService diseaseService;
    @Autowired
    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }




    @GetMapping("/diseases")
    public String getAllDiseases(Model model){
        List<Disease> diseases = diseaseService.findAll();
        model.addAttribute("diseaseList", diseases);
        return "diseases/disease-list";
    }


    @GetMapping("/diseases/{id}")
    public Disease getDisease(@PathVariable int id){
        return this.diseaseService.findById(id);
    }

    @GetMapping("/diseases/delete")
    public String delete(@RequestParam("diseaseIdToDelete")int id){
        this.diseaseService.deleteById(id);
        return "redirect:/diseases";
    }

    @PutMapping("/diseases/diseaseToEdit")
    public void updateDisease(@RequestBody Disease disease){
        this.diseaseService.save(disease);
    }

    @GetMapping("/diseases/add-disease-form")
    public String showAddFrom(Model model){
        Disease disease = new Disease();
        model.addAttribute("disease", disease);
        return "diseases/disease-form";
    }

    @PostMapping("/diseases/add-disease")
    public String addDisease(@Valid @ModelAttribute("disease") Disease disease, BindingResult result){
        if(result.hasErrors()){
            return "diseases/disease-form";
        }
        else{
            diseaseService.save(disease);
        }
        return "redirect:/diseases";
    }

    @GetMapping("/diseases/edit-disease-form")
    public String showUpdateForm(@RequestParam("diseaseIdToEdit") int id, Model model){
        Disease disease =this.diseaseService.findById(id);
        model.addAttribute("disease", disease);
        return "diseases/disease-form";
    }



}



