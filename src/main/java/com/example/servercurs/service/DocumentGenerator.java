package com.example.servercurs.service;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class DocumentGenerator {

    public String htmlToPdf(String processedHtml) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


            PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);


            DefaultFontProvider defaultFont = new DefaultFontProvider(true, true, true);

            ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("file:/D:/unik/sem6/курсовой/code/serverCurs/");

            converterProperties.setFontProvider(defaultFont);



            HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);

            /*FileOutputStream fout = new FileOutputStream("employee2.pdf");

            byteArrayOutputStream.writeTo(fout);
            byteArrayOutputStream.close();

            byteArrayOutputStream.flush();
            fout.close();*/
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())), pdfwriter);
            pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
            pdfDoc.close();

            return null;

    }
}
