package com.gustavo.stockflowapi.controllers;

import com.gustavo.stockflowapi.dtos.ProductDTO;
import com.gustavo.stockflowapi.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Creates a new Product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Received request to create product: {}", productDTO);
        ProductDTO createdProduct = service.create(productDTO);
        logger.info("Product created successfully with ID: {}", createdProduct.id());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProduct);
    }

    @Operation(summary = "Updates a existing Product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Received request to updated product with ID: {}", productDTO.id());
        ProductDTO updatedProduct = service.update(productDTO);
        logger.info("Product updated successfully with ID: {}", updatedProduct.id());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProduct);
    }

    @Operation(summary = "Finds a Product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @Parameter(name = "id", description = "Product ID", required = true)
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        logger.info("Receive request to find product with ID: {}", id);
        ProductDTO foundProduct = service.findById(id);
        logger.info("Product found with ID: {}", foundProduct.id());
        return ResponseEntity
                .ok()
                .body(foundProduct);
    }

    @Operation(summary = "List all Products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(produces = "application/json")
    public List<ProductDTO> listAll() {
        logger.info("Receive request to list all products");
        List<ProductDTO> productDTOList = service.findAll();
        logger.info("Returning list of {} products", productDTOList.size());
        return productDTOList;
    }

    @Operation(summary = "Deletes a Product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Parameter(name = "id", description = "Product ID", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Receive request to delete product with ID: {}", id);
        service.delete(id);
        logger.info("Product with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
