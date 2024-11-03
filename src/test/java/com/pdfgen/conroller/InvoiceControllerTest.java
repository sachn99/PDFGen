package com.pdfgen.conroller;


import com.pdfgen.controller.InvoiceController;
import com.pdfgen.model.InvoiceRequest;
import com.pdfgen.service.PDFService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PDFService pdfService;

    @Test
    public void testGeneratePdf_Error() throws Exception {
        InvoiceRequest invoiceRequest = new InvoiceRequest();

        Mockito.when(pdfService.generatePdf(invoiceRequest)).thenThrow(new RuntimeException("Test Exception"));

        mockMvc.perform(post("/api/pdf/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError());
    }
}
