package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.dtos.ProductDTO;
import com.gustavo.stockflowapi.exceptions.InvalidProductDataException;
import com.gustavo.stockflowapi.exceptions.ProductNotFoundException;
import com.gustavo.stockflowapi.exceptions.UnexpectedException;
import com.gustavo.stockflowapi.model.Product;
import com.gustavo.stockflowapi.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        logger.info("Validating product data: {}", productDTO);

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

        logger.info("Product data validation successful");
        return true;
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        logger.info("Creating product: {}", productDTO);

        if (productDataValidation(productDTO)) {
            ProductDTO savedProduct = new ProductDTO(repository.save(new Product(productDTO)));
            logger.info("Product created successfully: {}", savedProduct);
            return savedProduct;
        } else {
            logger.error("Error creating product");
            throw new UnexpectedException("Unexpected error! class ProductServiceImpl, method create");
        }
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        logger.info("Updating product with ID: {}", productDTO.id());
        Optional<Product> optionalProduct = repository.findById(productDTO.id());

        if (productDataValidation(productDTO)) {
            if (optionalProduct.isPresent()) {
                Product selectedProduct = optionalProduct.get();

                selectedProduct.setName(productDTO.name());
                selectedProduct.setDescription(productDTO.description());
                selectedProduct.setPrice(productDTO.price());
                selectedProduct.setQuantity(productDTO.quantity());

                ProductDTO updatedProduct = new ProductDTO(repository.save(selectedProduct));
                logger.info("Product updated successfully: {}", productDTO);
                return updatedProduct;
            } else {
                logger.warn("Product with ID {} not found", productDTO.id());
                throw new ProductNotFoundException("Product not found");
            }

        } else {
            logger.error("Error updating product");
            throw new UnexpectedException("Unexpect error! class ProductServiceImpl, method update");
        }
    }

    @Override
    public ProductDTO findById(Long id) {
        logger.info("Finding product by ID: {}", id);
        try {
            ProductDTO foundProduct = new ProductDTO(repository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found")));
            logger.info("Product found: {}", foundProduct);
            return foundProduct;
        } catch (ProductNotFoundException e) {
            logger.warn("Product with ID {} not found", id);
            throw e;
        }
    }

    @Override
    public List<ProductDTO> findAll() {
        logger.info("Finding all products");
        List<Product> productsList = repository.findAll();

        List<ProductDTO> productDTOList = productsList.stream()
                .map(ProductDTO::new)
                .toList();

        logger.info("Found {} products", productDTOList.size());
        return productDTOList;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting product with ID: {}", id);
        try {
            Optional<Product> optionalProduct = repository.findById(id);
            if (optionalProduct.isPresent()) {
                repository.deleteById(id);
                logger.info("Product with ID {} deleted successfully", id);
            } else {
                logger.warn("Product with ID {} not found", id);
                throw new ProductNotFoundException("Product not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting product: {}", e.getMessage());
            throw e;
        }
    }
}
