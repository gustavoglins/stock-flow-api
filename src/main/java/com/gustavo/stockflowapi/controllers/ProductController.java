package com.gustavo.stockflowapi.controllers;

import com.gustavo.stockflowapi.dtos.ProductDTO;
import com.gustavo.stockflowapi.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

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
        ProductDTO createdProduct = service.create(productDTO);
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
        ProductDTO updatedProduct = service.update(productDTO);
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
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO foundProduct = service.findById(id);
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
        return service.findAll();
    }

    @Operation(summary = "Deletes a Product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
