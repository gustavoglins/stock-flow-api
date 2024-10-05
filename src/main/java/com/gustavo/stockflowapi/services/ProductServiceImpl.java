package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.dtos.ProductDTO;
import com.gustavo.stockflowapi.exceptions.InvalidProductDataException;
import com.gustavo.stockflowapi.exceptions.ProductNotFoundException;
import com.gustavo.stockflowapi.exceptions.UnexpectedException;
import com.gustavo.stockflowapi.model.Product;
import com.gustavo.stockflowapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    public boolean productDataValidation(ProductDTO productDTO) {
        if (productDTO.name().isEmpty() || productDTO.name().isBlank()) {
            throw new InvalidProductDataException("Name cannot be empty or null");
        }
        if (productDTO.description().isEmpty() || productDTO.description().isBlank()) {
            throw new InvalidProductDataException("Description cannot be empty or null");
        }
        if (productDTO.price().doubleValue() < 0) {
            throw new InvalidProductDataException("Price must be greater than 0");
        }
        if (productDTO.quantity() == null) {
            throw new InvalidProductDataException("Quantity cannot be null, must be 0 or greater");
        }

        return true;
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        if (productDataValidation(productDTO)) {
            return new ProductDTO(repository.save(new Product(productDTO)));
        } else {
            throw new UnexpectedException("Unexpected error! class ProductServiceImpl, method create");
        }
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Optional<Product> optionalProduct = repository.findById(productDTO.id());

        if (productDataValidation(productDTO)) {
            if (optionalProduct.isPresent()) {
                Product selectedProduct = optionalProduct.get();

                selectedProduct.setName(productDTO.name());
                selectedProduct.setDescription(productDTO.description());
                selectedProduct.setPrice(productDTO.price());
                selectedProduct.setQuantity(productDTO.quantity());

                return new ProductDTO(repository.save(selectedProduct));
            } else {
                throw new ProductNotFoundException("Product not found");
            }

        } else {
            throw new UnexpectedException("Unexpect error! class ProductServiceImpl, method update");
        }
    }

    @Override
    public ProductDTO findById(Long id) {
        return new ProductDTO(repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found")));
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> productsList = repository.findAll();

        return productsList.stream()
                .map(ProductDTO::new)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }
}
