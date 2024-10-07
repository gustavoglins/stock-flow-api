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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Creates a new product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Receive request to create a new product: {}", productDTO);
        try {
            ProductDTO createdProduct = service.create(productDTO);
            logger.info("Product created successfully. Product ID: {}", createdProduct.id());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdProduct);
        } catch (Exception e) {
            logger.error("Failed to create product. Error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Operation(summary = "Updates a existing product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Received request to updated product with ID: {}", productDTO.id());
        try {
            ProductDTO updatedProduct = service.update(productDTO);
            logger.info("Product with ID: {}, updated successfully", updatedProduct.id());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updatedProduct);
        } catch (Exception e) {
            logger.error("Failed to updated product. Error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Operation(summary = "Finds a Product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @Parameter(name = "id", description = "Product ID", required = true)
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id) {
        logger.info("Receive request to find product with ID: {}", id);
        try {
            ProductDTO foundProduct = service.findById(id);
            logger.info("Product with ID: {}, found", foundProduct.id());
            return ResponseEntity
                    .ok()
                    .body(foundProduct);
        } catch (Exception e) {
            logger.info("Failed to find product. Error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Operation(summary = "Lists all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(produces = "application/json")
    public List<ProductDTO> listAll() {
        logger.info("Receive request to list all products");
        List<ProductDTO> productDTOList = service.findAll();
        try{
            logger.info("Returning list of {} products", productDTOList.size());
            return productDTOList;
        } catch (Exception e){
            logger.error("Failed to list all products. Error: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Operation(summary = "Deletes a product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Parameter(name = "id", description = "Product ID", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        logger.info("Receive request to delete product with ID: {}", id);
        try{
            service.delete(id);
            logger.info("Product with ID: {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            logger.error("Failed to delete product. Error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
