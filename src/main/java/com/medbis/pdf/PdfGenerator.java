package com.medbis.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.medbis.entity.Visit;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Data
public class PdfGenerator {

    private Visit visit;

    public PdfGenerator(Visit visit) {
        this.visit = visit;
    }


    public String generateFileName() {
        StringBuilder stringBuilder = new StringBuilder();
        String filename;
        stringBuilder.append(this.visit.getVisitDate())
                .append("-")
                .append(this.visit.getVisitId())
                .append(".pdf");

        filename = String.valueOf(stringBuilder);
        return filename;
    }


    public FileOutputStream createFile() throws FileNotFoundException {
        return new FileOutputStream(generateFileName());
    }

    public PdfWriter createPdfWriter() throws FileNotFoundException, DocumentException {
        return PdfWriter.getInstance(new Document(), createFile());
    }


    public void setDocumentContent(Document document) throws DocumentException {
        document.add(new Paragraph("Wizyta nr" + this.visit.getVisitId()));
        document.add(new Paragraph("Data wizyty: " + this.visit.getVisitDate()));
        document.add(new Paragraph("Imię i nazwisko pacjenta: " + this.visit.getPatient().getName() + " " + this.visit.getPatient().getSurname()));
        document.add(new Paragraph("Pielęgniarka: " + this.visit.getEmployee().getName() + " " + this.visit.getEmployee().getSurname()));
        //document.add(new Paragraph("Wykonane zabiegi: " + visit.getServices()));
    }

    public void createVisitRaport() {
        try {
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, createFile());
            document.open();
            setDocumentContent(document);
            document.close();
            pdfWriter.close();
        } catch (FileNotFoundException | DocumentException err) {
            err.printStackTrace();
        }
    }
}

