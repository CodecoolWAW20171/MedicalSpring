package com.medbis.pdf;

import com.itextpdf.text.Paragraph;
import com.medbis.entity.Visit;

class ContentGenerator {
    private Visit visit;
    private FontsHolder fontsHolder;


    Paragraph getTitle(){
        return new Paragraph("Raport z wizyty", fontsHolder.getFont("largeBoldFont"));
    }

    Paragraph getPatientInfo(){
        return  new Paragraph("Imię i nazwisko: " + this.visit.getPatient().getName() + " " + this.visit.getPatient().getSurname() + "\n" +
                        "PESEL: " + this.visit.getPatient().getPesel() +
                        "Adres: " + this.visit.getPatient().getZipCode() +", " + this.visit.getPatient().getCommunity() + "\n" +
                        this.visit.getPatient().getCity() + ", " + this.visit.getPatient().getStreet() + " " + this.visit.getPatient().getHouseNumber() + this.visit.getPatient().getApartmentNumber() +
                        fontsHolder.getFont("subFont"));
    }

    Paragraph getVisitInfo(){
        return new Paragraph("Data wizyty: " + visit.getVisitDate() + "\n" +
                        "Pielęgniarka przeprowadzajaca wizyte: " + this.visit.getEmployee().getName() + " " +
                        this.visit.getEmployee().getSurname(), fontsHolder.getFont("smallBoldFont"));
    }

    Paragraph getSubTitle(String subTitleType){
        Paragraph subTitle;
        switch (subTitleType){
            case "patient": subTitle = new Paragraph("Pacjent", fontsHolder.getFont("subFont"));
                            break;
            case "visit": subTitle = new Paragraph("Wizyta", fontsHolder.getFont("subFont"));
                            break;
            case "treatments": subTitle =  new Paragraph("Wykonane swiadczenia", fontsHolder.getFont("subFont"));
                            break;
            default:
                throw new IllegalStateException("Unexpected sub title type value: " + subTitleType);
        }
        return subTitle;
    }

    Paragraph getTreatmentsInfo(){
        return new Paragraph("swiadczenie", fontsHolder.getFont("subFont"));
    }


    ContentGenerator(Visit visit) {
        this.visit = visit;
        fontsHolder = FontsHolder.getInstance();
    }
}
