package com.medbis.controller;


import com.medbis.entity.Medicine;
import com.medbis.service.interfaces.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MedicineController {

    private MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    //ADDING NEW MEDICINE
    @GetMapping("/medicines/showFormForAddMedicine")
    public String showFormForAddMedicine(Model theModel){
        Medicine newMedicine = new Medicine();
        theModel.addAttribute("medicine", newMedicine);
        return "medicine/medicine-form";
    }

    //EDITING NEW MEDICINE
    @GetMapping ("/medicines/showFormForEditMedicine")
    public String showFormForEditMedicine(@RequestParam("medicineIdToEdit")int theId, Model theModel){
        Medicine newMedicine = medicineService.findById(theId);
        theModel.addAttribute("medicine", newMedicine);
        return "medicine/medicine-form";
    }

    @PostMapping("/medicines/addNewMedicine")
    public String addNewMedicine(@Valid @ModelAttribute("medicine")Medicine theMedicine, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "medicine/medicine-form";
        }else{
            medicineService.save(theMedicine);
            return "redirect:/medicines";
        }
    }

    //DELETING NEW MEDICINE
    @GetMapping("/medicines/delete")
    public String delete(@RequestParam("medicineIdToDelete")int theId){
        medicineService.deleteById(theId);
        return "redirect:/medicines";
    }

    @GetMapping ("/medicines")
    public String findAll(Model theModel){
        theModel.addAttribute("medicineList", medicineService.findAll());
        return "medicine/medicine-list";
    }

    @GetMapping ("/medicines/{id}")
    public Medicine findById(@PathVariable int id){
        Medicine theMedicine = medicineService.findById(id);
        if(theMedicine ==  null){
            throw  new RuntimeException("Medicine id not found: " + id);
        }
        return theMedicine;
    }

    @PostMapping("/medicines")
    public Medicine addMedicine(@RequestBody Medicine theMedicine){
        theMedicine.setMedicineId(0);
        medicineService.save(theMedicine);
        return theMedicine;
    }

    @PutMapping("/medicines")
    public Medicine updateMedicine(@RequestBody Medicine theMedicine){
        medicineService.save(theMedicine);
        return theMedicine;
    }

    @DeleteMapping("/medicines/{id}")
    public String deleteMedicineById(@PathVariable int id){
        Medicine theMedicine = medicineService.findById(id);
        if(theMedicine == null){
            throw new RuntimeException("Medicine id not found" + id);
        }
        medicineService.deleteById(id);
        return "Deleted medicine id: " + id;
    }



}
