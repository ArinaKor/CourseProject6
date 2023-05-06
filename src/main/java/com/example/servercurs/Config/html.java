/*
package com.example.servercurs.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class Html2PdfConfiguration {

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringWebHtmlToPdfConverter htmlToPdfConverter() {
        SpringWebHtmlToPdfConverter converter = new SpringWebHtmlToPdfConverter();
        converter.setPdfRenderer(createPdfRenderer());
        converter.setDocumentMargin(0, 0, 0, 0);
        converter.setPageSize(PageSize.A4);
        converter.setPageOrientation(Orientation.PORTRAIT);
        converter.setPrintBackground(true);
        converter.setDpi(300);
        converter.setJavaScriptEnabled(true);
        converter.setLinksEnabled(true);
        converter.setCreateExternalLinks(true);
        converter.setCreateInternalLinks(true);
        converter.setAddLineNumber(true);
        converter.setAddStyleLinks(true);
        converter.setAddLinkTags(true);
        converter.setAddAnchorLinks(true);
        converter.setAddTOC(true);
        converter.setAddHtmlHeader(true);
        converter.setAddHtmlFooter(true);
        converter.setAddPageCounter(true);
        converter.setAddWatermark(true);
        converter.setWatermarkOpacity(0.5f);
        converter.setWatermarkFontSize(50);
        converter.setWatermarkRotationAngle(45);
        converter.setWatermarkText("Watermark");
        converter.setWatermarkColor(ColorConstants.BLUE);
        converter.setWatermarkFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD));
        converter.setWatermarkHorizontalAlignment(HorizontalAlignment.CENTER);
        converter.setWatermarkVerticalAlignment(VerticalAlignment.MIDDLE);
        converter.setWatermarkBackground(new DeviceRgb(255, 255, 255));
        converter.setWatermarkBackgroundOpacity(0.5f);
        converter.setWatermarkImage(ImageDataFactory.create("src/main/resources/static/img/logo.png"));
        converter.setWatermarkImageOpacity(0.5f);
        converter.setWatermarkImageScale(0.5f);
        converter.setWatermarkImageHorizontalAlignment(HorizontalAlignment.CENTER);
        converter.setWatermarkImageVerticalAlignment(VerticalAlignment.MIDDLE);
        converter.setWatermarkImageRotationAngle(45);
        converter.setWatermarkImageRepeat(true);
        converter.setWatermarkImageTransparency(0.5f);
        converter.setWatermarkImageXOffset(50);
        converter.setWatermarkImageYOffset(50);
        return converter;
    }

    private PdfRenderer createPdfRenderer() {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        return new PdfRenderer(pdfDocument);
    }
}
*/
