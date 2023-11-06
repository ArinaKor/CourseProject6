package com.example.servercurs.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PdfController {


    public void generatePdf(HttpServletResponse response, SpringTemplateEngine templateEngine,Context dataContext) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"example.pdf\"");

            OutputStream outputStream = response.getOutputStream();
            // response.reset();

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            String html = templateEngine.process("Cert.html", new Context());
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes()));

            document.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Ошибка при генерации PDF-файла", e);
        }
    }
}