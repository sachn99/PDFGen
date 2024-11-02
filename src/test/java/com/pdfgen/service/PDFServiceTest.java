package com.pdfgen.service;

import com.pdfgen.model.InvoiceRequest;
import com.pdfgen.repository.PDFRepository;
import com.pdfgen.util.PDFGenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PDFServiceTest {

    @Mock
    private PDFRepository pdfRepository;

    @Mock
    private PDFGenUtil pdfGeneratorUtil;

    @InjectMocks
    private PDFService pdfService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPdf_ShouldReturnFile() {
        String fileName = "invoice_123.pdf";
        File expectedFile = new File("pdf_storage/" + fileName);

        when(pdfRepository.getFile(fileName)).thenReturn(expectedFile);

        File result = pdfService.getPdf(fileName);
        assertEquals(expectedFile, result);

        verify(pdfRepository, times(1)).getFile(fileName);
    }

    @Test
    public void testGetFileContent_ShouldReturnFileContent() throws IOException {
        File file = new File("pdf_storage/invoice_123.pdf");
        byte[] expectedContent = new byte[]{1, 2, 3};

        when(pdfRepository.getFileContent(file)).thenReturn(expectedContent);

        byte[] result = pdfService.getFileContent(file);
        assertArrayEquals(expectedContent, result);

        verify(pdfRepository, times(1)).getFileContent(file);
    }

    @Test
    public void testGetFileContent_WhenIOExceptionOccurs_ShouldThrowIOException() throws IOException {
        File file = new File("pdf_storage/invoice_123.pdf");

        when(pdfRepository.getFileContent(file)).thenThrow(new IOException("Test IOException"));

        assertThrows(IOException.class, () -> pdfService.getFileContent(file));
        verify(pdfRepository, times(1)).getFileContent(file);
    }
}
