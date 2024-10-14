package br.com.solid.infrastructure.reports.excel;

import br.com.solid.domain.dto.ProductDto;
import br.com.solid.utils.FactoryUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportExcelTest {

    @Test
    void generateReport_shouldReturnExcelFile() throws Exception {
        List<ProductDto> products = FactoryUtils.createSampleProducts();

        ReportExcel reportExcel = new ReportExcel();

        byte[] report = reportExcel.generateReport(products);

        assertNotNull(report);
        assertTrue(report.length > 0);

        try (Workbook workbook = new HSSFWorkbook(new ByteArrayInputStream(report))) {
            assertEquals(1, workbook.getNumberOfSheets());
            var sheet = workbook.getSheetAt(0);

            var headerRow = sheet.getRow(0);
            assertEquals("name", headerRow.getCell(0).getStringCellValue());
            assertEquals("price", headerRow.getCell(1).getStringCellValue());
            assertEquals("quantity", headerRow.getCell(2).getStringCellValue());

            var dataRow = sheet.getRow(1);
            assertEquals("Laptop", dataRow.getCell(0).getStringCellValue());
            assertEquals("10.00", dataRow.getCell(1).getStringCellValue());
            assertEquals("10", dataRow.getCell(2).getStringCellValue());
        }
    }
}