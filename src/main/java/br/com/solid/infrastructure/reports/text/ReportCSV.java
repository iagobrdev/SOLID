package br.com.solid.infrastructure.reports.text;

import br.com.solid.domain.dto.ProductDto;
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/**
 * Report generator for CSV format.
 *
 * <p>This class generates a CSV report for a given list of products, using the OpenCSV library
 * to format the data with semicolon (;) as the delimiter.</p>
 */
public class ReportCSV extends AbstractReportTextGenerator {

    /**
     * Writes the CSV header using the field names of {@link ProductDto}.
     *
     * <p>This method uses OpenCSV to write the header row based on the fields of the
     * {@link ProductDto} class.</p>
     *
     * @param baos The {@link ByteArrayOutputStream} to which the header will be written.
     * @param fields The fields of the {@link ProductDto} class to be used as headers.
     * @throws Exception if an error occurs while writing the header.
     */
    @Override
    protected void writeHeader(ByteArrayOutputStream baos, Field[] fields) throws Exception {
        try (var writer = new CSVWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8),
                ';', CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

            var header = Arrays.stream(fields)
                    .map(Field::getName)
                    .toArray(String[]::new);
            writer.writeNext(header);
        }
    }

    /**
     * Writes the CSV data rows for each product in the provided list.
     *
     * <p>This method writes the data rows for the CSV report, where each row corresponds
     * to a product in the {@link ProductDto} list. It handles the conversion of field values
     * to string and handles null values gracefully.</p>
     *
     * @param baos The {@link ByteArrayOutputStream} to which the data will be written.
     * @param fields The fields of the {@link ProductDto} class to be used as data.
     * @param product The {@link ProductDto} object representing the product data.
     * @throws Exception if an error occurs while writing the data.
     */
    @Override
    protected void writeData(ByteArrayOutputStream baos, Field[] fields, ProductDto product) throws Exception {
        try (var writer = new CSVWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8),
                ';', CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

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
                    .toArray(String[]::new);
            writer.writeNext(data);
        }
    }
}