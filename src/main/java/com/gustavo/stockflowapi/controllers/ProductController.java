package com.gustavo.stockflowapi.controllers;

import com.gustavo.stockflowapi.dtos.ProductDTO;
import com.gustavo.stockflowapi.services.ProductService;
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

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO createdProduct = service.create(productDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProduct);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO updatedProduct = service.update(productDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO foundProduct = service.findById(id);
        return ResponseEntity
                .ok()
                .body(foundProduct);
    }

    @GetMapping
    public List<ProductDTO> listAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
