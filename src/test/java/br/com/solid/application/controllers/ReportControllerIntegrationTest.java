package br.com.solid.application.controllers;

import br.com.solid.application.services.ReportServiceImpl;
import br.com.solid.utils.FactoryUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
class ReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportServiceImpl reportService;

    @Test
    void generateReport_shouldReturnCSVReport() throws Exception {
        when(reportService.generateReport("csv", FactoryUtils.createSampleProducts()))
                .thenReturn(FactoryUtils.createMockCsvReport().getBytes());

        mockMvc.perform(post("/generateReport?extension=csv")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(FactoryUtils.createSampleProductsJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string(FactoryUtils.createMockCsvReport()));
    }
}