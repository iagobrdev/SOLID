package br.com.solid.utils;

import br.com.solid.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Utility class for creating test data and mock configurations in a cleaner way.
 */
public class FactoryUtils {

    /**
     * Creates a list of sample products.
     *
     * @return A list of {@link ProductDto} with sample data.
     */
    public static List<ProductDto> createSampleProducts() {
        return List.of(
                new ProductDto("Laptop", new BigDecimal("10.00"), 10L),
                new ProductDto("Phone", new BigDecimal("5.00"), 20L),
                new ProductDto("Tablet", new BigDecimal("7.50"), 15L)
        );
    }

    /**
     * Creates a JSON string representation of the sample products.
     *
     * @return JSON string representing the sample product data.
     */
    public static String createSampleProductsJson() {
        return """
                [
                    {"name":"Laptop","price":10.00,"quantity":10},
                    {"name":"Phone","price":5.00,"quantity":20},
                    {"name":"Tablet","price":7.50,"quantity":15}
                ]
                """;
    }

    /**
     * Generates a mock CSV report string.
     *
     * @return CSV formatted string representing the report.
     */
    public static String createMockCsvReport() {
        return "product;price;quantity\nLaptop;10.00;10\nPhone;5.00;20\nTablet;7.50;15\n";
    }
}