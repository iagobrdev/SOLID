package br.com.solid.infrastructure.reports.interfaces;

import br.com.solid.domain.dto.ProductDto;

import java.util.List;

/**
 * Interface for generating product reports.
 *
 * <p>Implementations of this interface are responsible for generating reports
 * in various formats (e.g., CSV, TXT, XLS) based on the provided product data.</p>
 */
public interface ReportGenerator {

    /**
     * Generates a report for the provided list of products.
     *
     * @param productDto The list of {@link ProductDto} objects representing the product data.
     * @return A byte array containing the generated report.
     */
    byte[] generateReport(List<ProductDto> productDto);
}