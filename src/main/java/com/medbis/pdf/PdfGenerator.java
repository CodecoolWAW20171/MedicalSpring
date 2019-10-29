package com.medbis.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.medbis.entity.Visit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class PdfGenerator {

    private Visit visit;

    public PdfGenerator(Visit visit) {
        this.visit = visit;
    }

    private String generateFileName() {
        StringBuilder stringBuilder = new StringBuilder();
        String filename;
        stringBuilder.append(this.visit.getVisitDate())
                .append("-")
                .append(this.visit.getVisitId())
                .append(".pdf");

        filename = String.valueOf(stringBuilder);
        return filename;
    }


    private FileOutputStream createFile() throws FileNotFoundException {
        return new FileOutputStream(generateFileName());
    }

    private void addContent(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        ContentGenerator contentGenerator = new ContentGenerator(visit);

        addEmptyLine(preface, 1);
        preface.add(contentGenerator.getTitle());
        addEmptyLine(preface, 2);
        preface.add(contentGenerator.getSubTitle("patient"));
        addEmptyLine(preface,1);
        preface.add(contentGenerator.getPatientInfo());
        addEmptyLine(preface, 1);
        preface.add(contentGenerator.getSubTitle("visit"));
        addEmptyLine(preface,1);
        preface.add(contentGenerator.getVisitInfo());
        addEmptyLine(preface, 1);

       /*
        wyciagnac z bazy te swiadczenia todo
       */
        preface.add(contentGenerator.getSubTitle("treatments"));
        //dodane tylko testowo
        preface.add(new Paragraph("swiadczenie1"));
        preface.add(new Paragraph("swiadczenie2"));
        preface.add(new Paragraph("swiadczenie3"));
        addEmptyLine(preface,1);
        document.add(preface);
    }


    public void createVisitRaport() {
        try {
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, createFile());
            document.open();
            addContent(document);
            document.close();
            pdfWriter.close();
        } catch (FileNotFoundException | DocumentException err) {
            err.printStackTrace();
        }
    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


}

