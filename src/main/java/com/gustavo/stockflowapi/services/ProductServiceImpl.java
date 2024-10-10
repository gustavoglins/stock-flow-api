package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.dtos.ProductDTO;
import com.gustavo.stockflowapi.exceptions.InvalidProductDataException;
import com.gustavo.stockflowapi.exceptions.ProductNotFoundException;
import com.gustavo.stockflowapi.exceptions.UnexpectedException;
import com.gustavo.stockflowapi.domain.product.Product;
import com.gustavo.stockflowapi.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    public boolean productDataValidation(ProductDTO productDTO) {
        logger.info("Starting product data validation for: {}", productDTO);

        if (productDTO.name().isEmpty() || productDTO.name().isBlank()) {
            logger.error("Invalid product data: Name is empty or null");
            throw new InvalidProductDataException("Name cannot be empty or null");
        }
        if (productDTO.description().isEmpty() || productDTO.description().isBlank()) {
            logger.error("Invalid product data: Description is empty or null");
            throw new InvalidProductDataException("Description cannot be empty or null");
        }
        if (productDTO.price().doubleValue() < 0) {
            logger.error("Invalid product data: Price is less than 0");
            throw new InvalidProductDataException("Price must be greater than 0");
        }
        if (productDTO.quantity() == null) {
            logger.error("Invalid product data: Quantity is null");
            throw new InvalidProductDataException("Quantity cannot be null, must be 0 or greater");
        }

        logger.info("Product data validation successful for product: {}", productDTO);
        return true;
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        logger.debug("Creating product: {}", productDTO);

        if (productDataValidation(productDTO)) {
            ProductDTO savedProduct = new ProductDTO(repository.save(new Product(productDTO)));
            logger.debug("Product created successfully: {}", savedProduct);
            return savedProduct;
        } else {
            logger.error("Failed to create product due to unexpected error");
            throw new UnexpectedException("Unexpected error! class ProductServiceImpl, method create");
        }
    }

    @Override
    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        logger.debug("Updating product with ID: {}", productDTO.id());
        Optional<Product> optionalProduct = repository.findById(productDTO.id());

        if (productDataValidation(productDTO)) {
            if (optionalProduct.isPresent()) {
                Product selectedProduct = optionalProduct.get();

                selectedProduct.setName(productDTO.name());
                selectedProduct.setDescription(productDTO.description());
                selectedProduct.setPrice(productDTO.price());
                selectedProduct.setQuantity(productDTO.quantity());

                return new ProductDTO(repository.save(selectedProduct)); // Log success in Controller
            } else {
                logger.warn("Product with ID {} not found during update", productDTO.id());
                throw new ProductNotFoundException("Product not found");
            }
        } else {
            logger.error("Error updating product");
            throw new UnexpectedException("Unexpected error! class ProductServiceImpl, method update");
        }
    }

    @Override
    public ProductDTO findById(Long id) {
        logger.debug("Finding product by ID: {}", id);
        try {
            return new ProductDTO(repository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"))); // Log success in Controller
        } catch (ProductNotFoundException e) {
            logger.warn("Product with ID {} not found", id);
            throw e;
        }
    }

    @Override
    public List<ProductDTO> findAll() {
        logger.debug("Finding all products");
        List<Product> productsList = repository.findAll();
        return productsList.stream().map(ProductDTO::new).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.debug("Deleting product with ID: {}", id);
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            repository.deleteById(id);
        } else {
            logger.warn("Product with ID {} not found during deletion", id);
            throw new ProductNotFoundException("Product not found");
        }
    }

}
