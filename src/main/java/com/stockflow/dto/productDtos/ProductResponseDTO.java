package com.stockflow.dto.productDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.stockflow.model.product.Product;
import org.springframework.hateoas.Links;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"id", "name", "description", "price", "quantity", "link"})
public record ProductResponseDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("name")
        String name,

        @JsonProperty("description")
        String description,

        @JsonProperty("price")
        double price,

        @JsonProperty("quantity")
        Long quantity,

        @JsonProperty("links")
        Links links) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductResponseDTO(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getLinks());
    }
}