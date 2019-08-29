package com.medbis.controller;

import com.medbis.entity.Category;
import com.medbis.entity.Treatment;
import com.medbis.service.interfaces.CategoryService;
import com.medbis.service.interfaces.TreatmentService;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TreatmentController {

    private CategoryService categoryService;
    private TreatmentService treatmentService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService, CategoryService categoryService) {
        this.treatmentService = treatmentService;
        this.categoryService =  categoryService;
    }

    @GetMapping("/treatments")
    public String findAll(Model model){
        List<Treatment> treatments = this.treatmentService.findAll();
        System.out.println("Treatments:" + treatments.toString());
        model.addAttribute("treatments" , treatments);
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        return "treatment/treatments-list";
    }

    @GetMapping("/treatments/{id}")
    public String findById(@PathVariable("id") int id){
        Treatment treatment = this.treatmentService.findById(id);
        if(treatment==null){
            throw new RuntimeException("Treatment with id:" + id + "not found");
        }
        return "treatment/treatmentById";
    }

    @GetMapping("/treatments/show-treatment-form")
    public String showAddTreatmentForm(Model model){
        Treatment treatment = new Treatment();
        model.addAttribute("treatment", treatment);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "treatment/treatment-form";
    }

    /*
        GET treatments/show-form       -> display form
        POST treatments/add-treatment  -> display form (with errors)   --> someone could bookmark this!
        POST treatments/add-treatment  -> redirect to /treatments
        GET treatments                 -> display list

        better:
        GET  treatments/add-treatment  -> display form
        POST treatments/add-treatment  -> display form (with errors)
        POST treatments/add-treatment  -> redirect to /treatments
        GET treatments                 -> display list

     */

    @PostMapping("/treatments/add-treatment")
    public String addTreatment(@Valid @ModelAttribute("treatment") Treatment treatment, BindingResult result){
        if(result.hasErrors()){
            return "treatment/treatment-form";
        }
        else{treatmentService.save(treatment);}
        return "redirect:/treatments";
    }

    @GetMapping("/treatments/delete")
    public String delete(@RequestParam("treatmentIdToDelete") int id){
        this.treatmentService.deleteById(id);
        return "redirect:/treatments";
    }

    @GetMapping("/treatments/show-edit-form")
    public String showEditTreatmentForm(@RequestParam("treatmentIdToEdit") int id, Model model){
        Treatment treatment = treatmentService.findById(id);
        model.addAttribute("treatment", treatment);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "treatment/treatment-form";
    }

    @PutMapping("/treatments/treatment-to-edit")
    public void updateTreatments(@RequestBody Treatment treatment){
        this.treatmentService.save(treatment);
    }

}
