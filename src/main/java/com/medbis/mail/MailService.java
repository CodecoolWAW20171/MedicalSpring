package com.medbis.mail;

import com.medbis.entity.Employee;
import com.medbis.entity.Patient;
import com.medbis.entity.Visit;
import com.medbis.service.interfaces.UserService;
import com.medbis.service.interfaces.VisitService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService implements Runnable {


    private MailCfg mailCfg;
    private MailContent mailContent;

    private Visit visit;
    private String action;

    private UserService employeeService;
    private UserService patientService;
    private VisitService visitService;


    MailService(MailCfg mailCfg, MailContent mailContent,@Qualifier("EmployeeServiceImpl") UserService employeeService, @Qualifier("PatientServiceImpl") UserService patientService, VisitService visitService){
        this.mailCfg = mailCfg;
        this.mailContent = mailContent;
        this.employeeService = employeeService;
        this.patientService = patientService;
        this.visitService  = visitService;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

   public SimpleMailMessage createMailMessage(String mail, MailDto mailDto){
       SimpleMailMessage mailMessage = new SimpleMailMessage();
       mailMessage.setFrom(mailCfg.getUsername());
       mailMessage.setTo(mail);
       mailMessage.setSubject(mailDto.getSubject());
       mailMessage.setText(mailDto.getMessage());
       return mailMessage;
   }
// respodnsvile for automatic mails//
   public SimpleMailMessage createMailMessage(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        Patient patient = (Patient) patientService.findById(visit.getVisitPatientId());
        mailMessage.setFrom(mailCfg.getUsername());
        mailMessage.setTo(patient.getMail());
        Employee employee = (Employee) employeeService.findById(visit.getVisitEmployeeId());

        switch(this.action) {
            case "deleteVisit":
                mailMessage.setSubject(mailContent.getDeleteVisitSubject());
                mailMessage.setText(mailContent.getDeleteVisitContent(visit, employee));
                break;
            case "addVisit":
                mailMessage.setSubject(mailContent.getAddVisitSubject());
                mailMessage.setText(mailContent.getNewVisitContent(visit, employee));
                break;
            case "editVisit":
                mailMessage.setSubject(mailContent.getEditVisitSubject());
                mailMessage.setText(mailContent.getEditVisitContent(visit, employee));
                break;

            default:
                System.out.println("Something with mail sending went wrong.");
        }

        return mailMessage;
    }

    private void sendMail(){
        SimpleMailMessage mailMessage = createMailMessage();
        MailBox.getInstance().send(mailMessage);
    }

    public void prepareMailToSend(Visit theVisit, int initialAmountOfPlannedVisit){
        Thread email = new Thread(this);
        this.setAction(visitService.setCorrectAction(initialAmountOfPlannedVisit));
        this.setVisit(theVisit);
        email.start();
    }


    @Override
    public void run() {
        sendMail();
    }

}
