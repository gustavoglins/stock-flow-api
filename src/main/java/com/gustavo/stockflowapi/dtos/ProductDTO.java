package com.gustavo.stockflowapi.dtos;

import com.gustavo.stockflowapi.model.Product;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public record ProductDTO(
        Long id,

        @NotBlank(message = "Name cannot be blank or empty")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @NotBlank(message = "Description cannot be blank or empty")
        @Size(max = 255, message = "Description cannot exceed 255 characters")
        String description,

        @NotNull(message = "Price cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Quantity cannot be null")
        @Min(value = 0, message = "Quantity must be 0 or greater")
        Integer quantity) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductDTO(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}