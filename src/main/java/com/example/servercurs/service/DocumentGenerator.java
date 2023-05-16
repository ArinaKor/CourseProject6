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


            DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);

            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFont);
            HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);
        /*PdfDocument pdfDoc = new PdfDocument(pdfwriter);
        pdfDoc.setDefaultPageSize(PageSize.A4.rotate());*/

            FileOutputStream fout = new FileOutputStream("employee3.pdf");


            byteArrayOutputStream.writeTo(fout);
            byteArrayOutputStream.close();

            byteArrayOutputStream.flush();
            fout.close();


            return null;

    }
}
