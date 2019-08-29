package com.medbis.mail;

import com.medbis.entity.Employee;
import com.medbis.entity.Visit;
import com.medbis.service.interfaces.UserService;
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

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public void setAction(String action) {
        this.action = action;
    }


    MailService(MailCfg mailCfg, MailContent mailContent,@Qualifier("EmployeeServiceImpl") UserService employeeService){
        this.mailCfg = mailCfg;
        this.mailContent = mailContent;
        this.employeeService = employeeService;
    }

   public SimpleMailMessage createMailMessage(String mail, MailDto mailDto){
       SimpleMailMessage mailMessage = new SimpleMailMessage();
       mailMessage.setFrom(mailCfg.getUsername());
       mailMessage.setTo(mail);
       mailMessage.setSubject(mailDto.getSubject());
       mailMessage.setText(mailDto.getMessage());
       return mailMessage;
   }

   public SimpleMailMessage createMailMessage(String mail, Visit visit, Employee employee, String action){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailCfg.getUsername());
        mailMessage.setTo(mail);

        switch(action) {
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

        public void sendMail(){
            Employee employee = (Employee) employeeService.findById(visit.getVisitEmployeeId());
            String mail = visit.getPatient().getMail();
            SimpleMailMessage mailMessage = createMailMessage(mail, visit, employee, action);
            MailBox.getInstance().send(mailMessage);
        }


    @Override
    public void run() {
        System.out.println("Powinien powstac nowy watek ");
        System.out.println("zaczynam wysylac o godz: " + System.currentTimeMillis());
        sendMail();
        System.out.println("Kokoncze wysylac o: " + System.currentTimeMillis());
    }
}
