package com.pdfgen.controller;

import com.pdfgen.model.InvoiceRequest;
import com.pdfgen.service.PDFService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private PDFService pdfService;
    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequest invoiceRequest) {
        logger.info("Received request to generate PDF for invoice: {}", invoiceRequest);
        try {
            String pdfFilePath = pdfService.generatePdf(invoiceRequest);
            logger.info("PDF generated successfully: {}", pdfFilePath);

            return ResponseEntity.ok("PDF generated and stored at: " + pdfFilePath);
        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam String fileName) {
        logger.info("Received request to download PDF {}", fileName);
        try {
            File pdfFile = pdfService.getPdf(fileName);

            byte[] pdfData = pdfService.getFileContent(pdfFile);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfData);
        } catch (Exception e) {
            logger.error("Unexpected error during PDF download", e);
            throw new RuntimeException("Unexpected error during PDF download", e);
        }
    }
}

