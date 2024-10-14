package br.com.solid.application.services;

import br.com.solid.domain.dto.ProductDto;
import br.com.solid.infrastructure.factories.ReportFactory;
import br.com.solid.infrastructure.reports.interfaces.ReportGenerator;
import br.com.solid.utils.FactoryUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportServiceImplTest {

    private ReportFactory reportFactory;
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        reportFactory = Mockito.mock(ReportFactory.class);
        reportService = new ReportServiceImpl(reportFactory);
    }

    @Test
    void generateReport_shouldReturnReport() {
        String extension = "csv";
        List<ProductDto> products = FactoryUtils.createSampleProducts();

        ReportGenerator reportGenerator = mock(ReportGenerator.class);
        when(reportFactory.getReportGenerator(extension)).thenReturn(reportGenerator);
        when(reportGenerator.generateReport(products)).thenReturn(new byte[0]);

        byte[] report = reportService.generateReport(extension, products);

        assertNotNull(report);
        verify(reportFactory, times(1)).getReportGenerator(extension);
        verify(reportGenerator, times(1)).generateReport(products);
    }
}