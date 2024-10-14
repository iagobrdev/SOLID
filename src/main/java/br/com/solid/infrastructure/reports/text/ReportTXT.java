package br.com.solid.infrastructure.reports.text;

import br.com.solid.domain.dto.ProductDto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/**
 * Report generator for plain text (TXT) format.
 *
 * <p>This class generates a plain text report for a given list of products,
 * with data fields separated by semicolons (;). It writes both the header
 * and data rows using UTF-8 encoding.</p>
 */
public class ReportTXT extends AbstractReportTextGenerator {

    /**
     * Writes the TXT header using the field names of {@link ProductDto}, separated by semicolons.
     *
     * <p>The header is written as a single line where each field name is separated by a semicolon.</p>
     *
     * @param baos The {@link ByteArrayOutputStream} to which the header will be written.
     * @param fields The fields of the {@link ProductDto} class to be used as headers.
     * @throws Exception if an error occurs while writing the header.
     */
    @Override
    protected void writeHeader(ByteArrayOutputStream baos, Field[] fields) throws Exception {
        try (var writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {

            var header = Arrays.stream(fields)
                    .map(Field::getName)
                    .reduce((field1, field2) -> field1 + ";" + field2)
                    .orElse("");

            writer.write(header + "\n");
            writer.flush();
        }
    }

    /**
     * Writes the TXT data rows for each product in the provided list, with fields separated by semicolons.
     *
     * <p>The data is written as rows where each field value is separated by a semicolon (;).
     * Null values are handled gracefully and converted to an empty string.</p>
     *
     * @param baos The {@link ByteArrayOutputStream} to which the data will be written.
     * @param fields The fields of the {@link ProductDto} class to be used as data.
     * @param product The {@link ProductDto} object representing the product data.
     * @throws Exception if an error occurs while writing the data.
     */
    @Override
    protected void writeData(ByteArrayOutputStream baos, Field[] fields, ProductDto product) throws Exception {
        try (var writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {

            var data = Arrays.stream(fields)
                    .map(field -> {
                        try {
                            field.setAccessible(true);
                            var value = field.get(product);
                            return Objects.nonNull(value) ? value.toString() : "";
                        } catch (Exception e) {
                            return "";
                        }
                    })
                    .reduce((value1, value2) -> value1 + ";" + value2)
                    .orElse("");

            writer.write(data + "\n");
            writer.flush();
        }
    }
}