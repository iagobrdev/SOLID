package br.com.solid.infrastructure.factories;

import br.com.solid.infrastructure.reports.excel.ReportExcel;
import br.com.solid.infrastructure.reports.interfaces.ReportGenerator;
import br.com.solid.infrastructure.reports.text.ReportCSV;
import br.com.solid.infrastructure.reports.text.ReportTXT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportFactoryTest {

    private ReportFactory reportFactory;

    @BeforeEach
    void setUp() {
        reportFactory = new ReportFactory();
    }

    @Test
    void getReportGenerator_shouldReturnReportCSV() {
        ReportGenerator reportGenerator = reportFactory.getReportGenerator("csv");

        assertTrue(reportGenerator instanceof ReportCSV);
    }

    @Test
    void getReportGenerator_shouldReturnReportTXT() {
        ReportGenerator reportGenerator = reportFactory.getReportGenerator("txt");

        assertTrue(reportGenerator instanceof ReportTXT);
    }

    @Test
    void getReportGenerator_shouldReturnReportExcel() {
        ReportGenerator reportGenerator = reportFactory.getReportGenerator("xls");

        assertTrue(reportGenerator instanceof ReportExcel);
    }

    @Test
    void getReportGenerator_withUnsupportedExtension_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                reportFactory.getReportGenerator("pdf"));
        assertEquals("Unsupported file extension: pdf", exception.getMessage());
    }
}
