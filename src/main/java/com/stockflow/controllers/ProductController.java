package com.stockflow.controllers;

import com.stockflow.dto.ProductDTO;
import com.stockflow.services.ProductService;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Received request to create a new product.");
        ProductDTO createdProduct = service.create(productDTO);
        logger.info("Request to create a new product processed successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Received request to update a product.");
        ProductDTO updatedProduct = service.update(productDTO);
        logger.info("Request to update product with ID: {} processed successfully.", productDTO.id());
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id) {
        logger.info("Received request to find product by id.");
        ProductDTO foundProduct = service.findById(id);
        logger.info("Request to find product with ID: {} processed successfully.", id);
        return ResponseEntity.ok(foundProduct);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> listAll() {
        logger.info("Received request to list all products.");
        List<ProductDTO> productDTOList = service.listAll();
        logger.info("Request to list all products processed successfully. Total products: {}", productDTOList.size());
        return ResponseEntity.ok(productDTOList);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Received request to delete product by id.");
        service.delete(id);
        logger.info("Request to delete product with ID: {} processed successfully.", id);
        return ResponseEntity.ok("Product with ID: " + id + " deleted successfully.");
    }
}
