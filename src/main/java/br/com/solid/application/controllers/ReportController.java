package br.com.solid.application.controllers;

import br.com.solid.application.services.ReportServiceImpl;
import br.com.solid.domain.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller responsible for handling product report generation.
 *
 * <p>This controller provides an API to generate reports in various formats,
 * such as CSV, TXT, and XLSX, based on the file extension provided by the user.</p>
 */
@RestController
public class ReportController {

    private final ReportServiceImpl reportServiceImpl;

    /**
     * Constructor that injects the report service.
     *
     * @param reportServiceImpl The service responsible for generating reports.
     */
    @Autowired
    public ReportController(ReportServiceImpl reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    /**
     * API endpoint to generate a product report.
     *
     * <p>This method generates a report based on the provided data and file extension.
     * The report is returned as a downloadable file.</p>
     *
     * @param extension The file extension that determines the report format (e.g., csv, txt, xlsx).
     * @param data The list of {@link ProductDto} objects representing the product data to be included in the report.
     * @return A {@link ResponseEntity} containing the generated report as a byte array and the appropriate headers.
     */
    @PostMapping("/generateReport")
    public ResponseEntity<byte[]> generateReport(@RequestParam("extension") String extension, @RequestBody List<ProductDto> data) {
        byte[] report = reportServiceImpl.generateReport(extension, data);
        var fileName = "report." + extension;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(getMediaTypeForFileExtension(extension))
                .body(report);
    }

    /**
     * Determines the correct {@link MediaType} based on the file extension.
     *
     * @param extension The file extension (e.g., csv, txt, xlsx).
     * @return The corresponding {@link MediaType} for the file extension.
     */
    private MediaType getMediaTypeForFileExtension(String extension) {
        return switch (extension.toLowerCase()) {
            case "csv", "txt" -> MediaType.TEXT_PLAIN;
            case "xls", "xlsx" -> MediaType.APPLICATION_OCTET_STREAM;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}