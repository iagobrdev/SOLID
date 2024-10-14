package br.com.solid.application.services;

import br.com.solid.domain.dto.ProductDto;
import br.com.solid.infrastructure.factories.ReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for generating product reports.
 *
 * <p>This service acts as a bridge between the controller and the report generation logic,
 * handling the process of selecting the appropriate report generator based on the file extension.</p>
 */
@Service
public class ReportServiceImpl {

    private final ReportFactory reportFactory;

    /**
     * Constructor that injects the {@link ReportFactory}.
     *
     * @param reportFactory The factory responsible for providing the correct report generator.
     */
    @Autowired
    public ReportServiceImpl(ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }

    /**
     * Generates a product report in the specified format.
     *
     * <p>This method uses the {@link ReportFactory} to select the appropriate report generator
     * based on the provided file extension and generates the report from the given product data.</p>
     *
     * @param extension The file extension that determines the report format (e.g., csv, txt, xlsx).
     * @param data The list of {@link ProductDto} objects representing the product data to be included in the report.
     * @return A byte array containing the generated report.
     */
    public byte[] generateReport(String extension, List<ProductDto> data) {
        var reportGenerator = reportFactory.getReportGenerator(extension);
        return reportGenerator.generateReport(data);
    }
}