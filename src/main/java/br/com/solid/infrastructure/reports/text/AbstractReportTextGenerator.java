package br.com.solid.infrastructure.reports.text;

import br.com.solid.domain.dto.ProductDto;
import br.com.solid.infrastructure.reports.interfaces.ReportGenerator;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Abstract class for generating text-based product reports (e.g., CSV, TXT).
 *
 * <p>This class provides common functionality for generating reports in text-based formats,
 * delegating the specific implementations of header and data writing to subclasses.</p>
 */
public abstract class AbstractReportTextGenerator implements ReportGenerator {

    /**
     * Generates a text-based report for the provided list of products.
     *
     * <p>This method is common for all text-based report generators and calls the
     * abstract methods {@link #writeHeader(ByteArrayOutputStream, Field[])} and
     * {@link #writeData(ByteArrayOutputStream, Field[], ProductDto)} to handle
     * the specific formatting of the report.</p>
     *
     * @param productList The list of {@link ProductDto} representing the product data.
     * @return A byte array containing the generated text-based report.
     */
    @Override
    public byte[] generateReport(List<ProductDto> productList) {
        var baos = new ByteArrayOutputStream();

        try {
            Field[] fields = ProductDto.class.getDeclaredFields();

            writeHeader(baos, fields);

            for (ProductDto product : productList) {
                writeData(baos, fields, product);
            }
        } catch (Exception e) {
            throw new RuntimeException("generateReport error ->" + e.getMessage());
        }

        return baos.toByteArray();
    }

    /**
     * Writes the header for the text-based report.
     *
     * <p>This method must be implemented by subclasses to handle the formatting
     * of the header for the specific text format (e.g., CSV, TXT).</p>
     *
     * @param baos The {@link ByteArrayOutputStream} to which the header will be written.
     * @param fields The fields of the {@link ProductDto} class to be used as headers.
     */
    protected abstract void writeHeader(ByteArrayOutputStream baos, Field[] fields) throws Exception;

    /**
     * Writes the data rows for the text-based report.
     *
     * <p>This method must be implemented by subclasses to handle the formatting
     * of the data rows for the specific text format (e.g., CSV, TXT).</p>
     *
     * @param baos The {@link ByteArrayOutputStream} to which the data will be written.
     * @param fields The fields of the {@link ProductDto} class to be used as data.
     * @param product The {@link ProductDto} object representing the product data.
     */
    protected abstract void writeData(ByteArrayOutputStream baos, Field[] fields, ProductDto product) throws Exception;
}