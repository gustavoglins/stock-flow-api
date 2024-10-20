package com.stockflow.controllers;

import com.stockflow.dto.ProductDTO;
import com.stockflow.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
@Tag(name = "Product", description = "Endpoints for product actions.")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(
            summary = "Create a new product",
            description = "Create a new product",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content())
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Received request to create a new product.");
        ProductDTO createdProduct = service.create(productDTO);
        logger.info("Request to create a new product processed successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @Operation(
            summary = "Update a product",
            description = "Update a product",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Received request to update a product.");
        ProductDTO updatedProduct = service.update(productDTO);
        logger.info("Request to update product with ID: {} processed successfully.", productDTO.id());
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(
            summary = "Find a product by ID",
            description = "Find a product by ID",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id) {
        logger.info("Received request to find product by id.");
        ProductDTO foundProduct = service.findById(id);
        logger.info("Request to find product with ID: {} processed successfully.", id);
        return ResponseEntity.ok(foundProduct);
    }

    @Operation(
            summary = "List all products registered",
            description = "List all products registered",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> listAll() {
        logger.info("Received request to list all products registered.");
        List<ProductDTO> productDTOList = service.listAll();
        logger.info("Request to list all products processed successfully. Total products: {}", productDTOList.size());
        return ResponseEntity.ok(productDTOList);
    }

    @Operation(
            summary = "Delete a product by ID",
            description = "Delete a product by ID",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Received request to delete product by id.");
        service.delete(id);
        logger.info("Request to delete product with ID: {} processed successfully.", id);
        return ResponseEntity.ok("Product with ID: " + id + " deleted successfully.");
    }
}
