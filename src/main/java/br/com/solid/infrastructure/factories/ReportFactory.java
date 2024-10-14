package br.com.solid.infrastructure.factories;

import br.com.solid.infrastructure.reports.excel.ReportExcel;
import br.com.solid.infrastructure.reports.interfaces.ReportGenerator;
import br.com.solid.infrastructure.reports.text.ReportCSV;
import br.com.solid.infrastructure.reports.text.ReportTXT;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Factory responsible for providing the appropriate {@link ReportGenerator}
 * based on the file extension.
 *
 * <p>This factory uses a mapping between file extensions and their corresponding
 * report generators, allowing the application to generate reports in different formats.</p>
 */
@Component
public class ReportFactory {

    private final Map<String, ReportGenerator> generatorMap = new HashMap<>();

    /**
     * Constructor that initializes the map of file extensions to their respective report generators.
     *
     * <p>The factory supports CSV, TXT, XLS, and XLSX formats by default.</p>
     */
    public ReportFactory() {
        generatorMap.put("csv", new ReportCSV());
        generatorMap.put("txt", new ReportTXT());
        generatorMap.put("xls", new ReportExcel());
        generatorMap.put("xlsx", new ReportExcel());
    }

    /**
     * Retrieves the appropriate {@link ReportGenerator} based on the file extension.
     *
     * <p>If the provided file extension is not supported, an {@link IllegalArgumentException}
     * is thrown.</p>
     *
     * @param fileExtension The file extension (e.g., csv, txt, xls, xlsx).
     * @return The corresponding {@link ReportGenerator} for the given file extension.
     * @throws IllegalArgumentException if the file extension is unsupported.
     */
    public ReportGenerator getReportGenerator(String fileExtension) {
        var generator = generatorMap.get(fileExtension.toLowerCase());

        if (Objects.isNull(generator)) {
            throw new IllegalArgumentException("Unsupported file extension: " + fileExtension);
        }
        return generator;
    }
}