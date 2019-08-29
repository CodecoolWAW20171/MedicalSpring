package com.medbis.mail;

import com.medbis.controller.PatientController;
import com.medbis.entity.Patient;
import com.medbis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping(
        method={RequestMethod.POST,RequestMethod.GET}
)
@Controller
public class MailController {

    private MailService mailService;
    private UserService userService;

    public MailController(MailService mailService, @Qualifier(value = "PatientServiceImpl")UserService userService, PatientController patientController) {
        this.mailService = mailService;
        this.userService = userService;
    }

    @GetMapping("/mails/show-mail-form")
    public String  sendMail(@RequestParam("patientId")String id, Model model) {
        Patient patient = (Patient) userService.findById(Integer.parseInt(id));
        model.addAttribute("mailDto", new MailDto());
        model.addAttribute("patient", patient);
        return "mails/mail-form";
    }

    @PostMapping("/mails/send-mail")
    public String sendMail(RedirectAttributes redirectAttributes, @Valid @ModelAttribute(value = "mailDto")MailDto mailDto, BindingResult bindingResult, @RequestParam("patientId") String id){
        if(bindingResult.hasErrors()){
            redirectAttributes.addAttribute("patientId", id);
            return "redirect:/mails/show-mail-form";
        }
        try {
            Patient patient = (Patient) userService.findById(Integer.parseInt(id));
            SimpleMailMessage mailMessage = mailService.createMailMessage(patient.getMail(), mailDto);
            MailBox.getInstance().send(mailMessage);
        }
        catch (NumberFormatException err){
            err.printStackTrace();
        }
        return "redirect:/patients";
    }
}

