package com.stockflow.dto.productDtos;

import com.stockflow.model.product.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.Links;

import java.io.Serial;
import java.io.Serializable;

public record ProductRequestDTO(

        Long id,

        @NotBlank(message = "Product name cannot be blank or empty")
        String name,

        @NotBlank(message = "Product description cannot be blank or empty")
        String description,

        @Min(message = "Product price must be greater than or equal to 0", value = 0)
        double price,

        @NotNull(message = "The product quantity must be greater than or equal to 0")
        @Min(message = "The product quantity must be greater than or equal to 0", value = 0)
        Long quantity,

        Links links) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductRequestDTO(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getLinks());
    }
}