package br.com.solid.infrastructure.reports.excel;

import br.com.solid.domain.dto.ProductDto;
import br.com.solid.infrastructure.reports.interfaces.ReportGenerator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Report generator for Excel format (.xls).
 *
 * <p>This class uses Apache POI to generate reports in Excel format.
 * It builds the report by creating the workbook, sheet, header, and data rows
 * for the product information.</p>
 */
public class ReportExcel implements ReportGenerator {

    private final Workbook workbook;
    private final Sheet sheet;

    /**
     * Constructor that initializes the workbook and creates a new sheet for the report.
     */
    public ReportExcel() {
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet("Report");
    }

    /**
     * Generates an Excel report for the provided list of products.
     *
     * @param productList The list of {@link ProductDto} representing the product data.
     * @return A byte array containing the generated Excel report.
     */
    @Override
    public byte[] generateReport(List<ProductDto> productList) {
        var baos = new ByteArrayOutputStream();

        try {
            var fields = ProductDto.class.getDeclaredFields();

            writeHeader(fields);
            writeDataRows(productList, fields);

            writeWorkbookToStream(baos);
        } catch (Exception e) {
            throw new RuntimeException("Error generating report: " + e.getMessage());
        }

        return baos.toByteArray();
    }

    /**
     * Writes the header row for the Excel report using the field names from {@link ProductDto}.
     *
     * @param fields The fields of the {@link ProductDto} class to be used as headers.
     */
    private void writeHeader(Field[] fields) {
        try {
            var headerRow = sheet.createRow(0);
            var colNum = 0;

            for (var field : fields) {
                headerRow.createCell(colNum++).setCellValue(field.getName());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error writing header: " + e.getMessage());
        }
    }

    /**
     * Writes the data rows for the Excel report using the provided product list.
     *
     * @param productList The list of {@link ProductDto} representing the product data.
     * @param fields The fields of the {@link ProductDto} class to be used as data.
     */
    private void writeDataRows(List<ProductDto> productList, Field[] fields) {
        try {
            int rowCount = 1;
            for (ProductDto product : productList) {
                var dataRow = sheet.createRow(rowCount++);
                writeDataRow(dataRow, fields, product);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error writing data rows: " + e.getMessage());
        }
    }

    /**
     * Writes a single data row for a given product.
     *
     * @param dataRow The row in the sheet where data will be written.
     * @param fields The fields of the {@link ProductDto} class to be used as data.
     * @param product The {@link ProductDto} object representing the product data.
     */
    private void writeDataRow(Row dataRow, Field[] fields, ProductDto product) {
        try {
            var colNum = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                var value = field.get(product);
                dataRow.createCell(colNum++).setCellValue(value != null ? value.toString() : "");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error writing data to row: " + e.getMessage());
        }
    }

    /**
     * Writes the workbook to the output stream.
     *
     * @param baos The {@link ByteArrayOutputStream} where the workbook will be written.
     */
    private void writeWorkbookToStream(ByteArrayOutputStream baos) {
        try {
            workbook.write(baos);
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Error writing workbook to stream: " + e.getMessage());
        }
    }
}