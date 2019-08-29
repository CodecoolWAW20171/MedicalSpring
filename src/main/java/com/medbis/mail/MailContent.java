package com.medbis.mail;

import com.medbis.entity.Employee;
import com.medbis.entity.Visit;
import org.springframework.stereotype.Component;

@Component
public class MailContent {

    String getNewVisitContent(Visit visit, Employee employee) {
        return "Dzień dobry,\n" +
                "Przypominamy o zaplanowanej wizycie, która odbędzie się dn. " +
                visit.getVisitDate() +
                ". " +
                "Wizytę przeprowadzi " +
                employee.getName() +
                employee.getSurname() +
                " " +
                " W celu odwołania lub przełożenia wizyty prosimy o kontakt przynajmniej 24h przed planowaną wizytą. \n" +
                "\n\nPozdrawiamy,\nBiuro Medical Spring";
    }

    String getDeleteVisitContent(Visit visit, Employee employee) {
        return "Dzień dobry,\n\n"  +
                "Informujemy, że wizyta pielęgniarki "+ employee.getName() + " " + employee.getSurname() +
                " zaplanowana na dzień " + visit.getVisitDate() + " została odwołana.\n" +
                "W celu umówienia nowej wizyty prosimy o kontakt z ww. pielęgniarką pod nr. telefonu: " +
                employee.getWorkPhoneNumber() +".\n\nPozdrawiamy,\nBiuro Medical Spring";
    }

     String getEditVisitContent(Visit visit, Employee employee) {
        return "Dzień dobry,\n\n"  +
                "Informujemy, że wizyta pielęgniarki "+ employee.getName() + " " + employee.getSurname() +
                " została przełożona na dzień " + visit.getVisitDate() + ".\n" +
                "W razie pytań prosimy o kontakt z ww. pielęgniarką pod nr. telefonu: " +
                employee.getWorkPhoneNumber() +".\n\nPozdrawiamy,\nBiuro Medical Spring";
    }

    String getAddVisitSubject(){
        return "Potwierdzenie umowienia wizyty";
    }

    String getEditVisitSubject(){
        return "Zmiana terminu wizyty";
    }
    String getDeleteVisitSubject(){
        return "Odwołanie wizyty";
    }


}
