package com.medbis.controller;

import com.medbis.dto.PasswordChangerDto;
import com.medbis.entity.Employee;
import com.medbis.factory.UserFactory;
import com.medbis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class EmployeeController {

    private UserService userService;
    private UserFactory userFactory;

    @Autowired
    public EmployeeController(@Qualifier(value = "EmployeeServiceImpl") UserService userService, UserFactory userFactory) {
        this.userService = userService;
        this.userFactory = userFactory;
    }

    @GetMapping("/employees")
    public String findAll(Model theModel) {
        theModel.addAttribute("employees", userService.findAll());
        return "users/employee-list";
    }

    @GetMapping ("/employees/show-employee-details")
    public String showPatientDetails(@RequestParam("id")int id, Model model){
        Employee employee = (Employee) userService.findById(id);
        model.addAttribute("employee", employee);
        return "users/employee-details";
    }

    //ADDING NEW Employee
    @GetMapping("/employees/showFormForAddEmployee")
    public String showFormForAddEmployee(Model model) {
        Employee employee = (Employee) userFactory.getNewUser("employee");
        model.addAttribute("employee", employee);
        return "users/employee-form";
    }


    @PostMapping("/employees/addNewEmployee")
    public String addNewEmployee(
            @Valid @ModelAttribute("employee") Employee theEmployee,
            BindingResult result) {
        if (result.hasErrors()) {
            return "users/employee-form";
        } else {
            userService.save(theEmployee);
            return "redirect:/employees";
        }
    }

    //EDITING NEW Employee
    @GetMapping("/employees/showFormForEditEmployee")
    public String showFormForEditMedicine(@RequestParam("employeeIdToEdit") int theId,
                                          Model theModel) {
        Employee employee = (Employee) userService.findById(theId);
        theModel.addAttribute("employee", employee);
        return "users/employee-form";
    }

    //DELETING NEW Employee
    @GetMapping("/employees/delete")
    public String delete(@RequestParam("employeeIdToDelete") int theId) {
        userService.deleteById(theId);
        return "redirect:/employees";
    }

    public Employee findByName(String name) {
        return (Employee) userService.findByName(name);
    }



    @GetMapping("/employees/change-password-form")
    public String showFormForChangingPassword(PasswordChangerDto passwordChangerDto, Model model) {
            model.addAttribute("passwordChangerDto", passwordChangerDto);
            return "users/password-changer";
    }

    @PostMapping("employees/change-password")
    public String changePassword(@Valid @ModelAttribute("dto") PasswordChangerDto dto, BindingResult bindingResult, BCryptPasswordEncoder bCryptPasswordEncoder) {
        String employeeName = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = (Employee) userService.findByName(employeeName);
        if (!bindingResult.hasErrors()) {
            if(bCryptPasswordEncoder.matches(dto.getOldPassword(), employee.getPassword())) {
                    if (dto.getNewPassword().equals(dto.getConfirmedPassword())){
                        employee.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
                        employee.setPasswordChanged(true);
                        userService.save(employee);
                        return "redirect:/patients";
                    }
                }
            }
        return "redirect:/employees/change-password-form";
    }
}




