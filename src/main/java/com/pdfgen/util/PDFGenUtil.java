package com.pdfgen.util;


import com.pdfgen.model.InvoiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;


import java.io.*;

@Component
public class PDFGenUtil {

    private static final Logger logger = LoggerFactory.getLogger(PDFGenUtil.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String generateInvoicePdf(InvoiceRequest invoiceRequest, File pdfFile) {
        logger.info("Starting PDF generation for invoice request.");

        Context context = new Context();
        context.setVariable("invoice", invoiceRequest);

        try (FileOutputStream os = new FileOutputStream(pdfFile)) {
            String htmlContent = templateEngine.process("invoice", context);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
            logger.info("PDF generated successfully for invoice request.");

            return pdfFile.getName();
        } catch (IOException e) {
            logger.error("IOException during PDF generation for invoice request.", e);
            throw new RuntimeException("Error during PDF generation", e);
        } catch (Exception e) {
            logger.error("Unexpected error in PDF generation", e);
            throw new RuntimeException("Unexpected error in PDF generation", e);
        }
    }
}
