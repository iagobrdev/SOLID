package br.com.solid.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) representing a product.
 *
 * <p>This DTO is used to transfer product data between different layers of the application.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The price of the product.
     */
    private BigDecimal price;

    /**
     * The quantity of the product available.
     */
    private Long quantity;
}