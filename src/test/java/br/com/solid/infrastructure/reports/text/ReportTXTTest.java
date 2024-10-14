package br.com.solid.infrastructure.reports.text;

import br.com.solid.domain.dto.ProductDto;
import br.com.solid.utils.FactoryUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportTXTTest {

    @Test
    void generateReport_shouldReturnTXTFile() throws Exception {

        List<ProductDto> products = FactoryUtils.createSampleProducts();

        ReportTXT reportTXT = new ReportTXT();

        byte[] report = reportTXT.generateReport(products);

        assertNotNull(report);
        assertTrue(report.length > 0);

        try (var reader = new InputStreamReader(new ByteArrayInputStream(report))) {
            char[] buffer = new char[report.length];
            int length = reader.read(buffer);
            String result = new String(buffer, 0, length);

            assertTrue(result.contains("name;price;quantity"));
            assertTrue(result.contains("Laptop;10.00;10"));
            assertTrue(result.contains("Phone;5.00;20"));
            assertTrue(result.contains("Tablet;7.50;15"));
        }
    }
}