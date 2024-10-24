package com.stockflow.services;

import com.stockflow.controllers.ProductController;
import com.stockflow.dto.productDtos.ProductDTO;
import com.stockflow.exceptions.ProductNotFoundException;
import com.stockflow.model.product.Product;
import com.stockflow.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        logger.info("Creating a new product ({}).", productDTO.name());
        Product createdProduct = repository.save(new Product(productDTO));

        createdProduct.add(linkTo(methodOn(ProductController.class).create(productDTO)).withSelfRel()); // Adding link hateoas

        logger.info("Product created successfully with ID: {}.", createdProduct.getId());
        return new ProductDTO(createdProduct);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        logger.info("Updating a product with ID: {}.", productDTO.id());
        Optional<Product> optionalProduct = repository.findById(productDTO.id());
        if (optionalProduct.isPresent()) {
            Product foundProduct = optionalProduct.get();
            logger.info("Product found for update: {}.", productDTO);

            foundProduct.setName(productDTO.name());
            foundProduct.setDescription(productDTO.description());
            foundProduct.setPrice(productDTO.price());
            foundProduct.setQuantity(productDTO.quantity());

            foundProduct.add(linkTo(methodOn(ProductController.class).update(productDTO)).withSelfRel()); // Adding link hateoas

            ProductDTO updatedProduct = new ProductDTO(repository.save(foundProduct));
            logger.info("Product with ID: {} updated successfully.", productDTO.id());
            return updatedProduct;
        }
        logger.info("Product with ID: {} not found for update.", productDTO.id());
        throw new ProductNotFoundException("Product with ID: " + productDTO.id() + " not found.");
    }

    @Override
    public ProductDTO findById(Long id) {
        logger.info("Searching for product with ID: {}", id);
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            Product foundProduct = optionalProduct.get();

            foundProduct.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel()); // Adding link hateoas

            logger.info("Product with ID: {} found successfully.", foundProduct.getId());
            return new ProductDTO(foundProduct);
        } else {
            logger.info("Product with ID: {} not found.", id);
            throw new ProductNotFoundException("Product with ID: " + id + " not found.");
        }
    }

    @Override
    public List<ProductDTO> listAll() {
        logger.info("Listing all registered products.");
        List<Product> productList = repository.findAll();

        productList.forEach(product -> product.add(linkTo(methodOn(ProductController.class).listAll()).withSelfRel())); // Adding link hateoas

        logger.info("Total products found: {}", productList.size());
        return productList.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting product with ID: {}", id);
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            repository.deleteById(id);
            logger.info("Product with ID: {} deleted successfully.", id);
        } else {
            logger.info("Product with ID: {} not found.", id);
            throw new ProductNotFoundException("Product with ID: " + id + " not found.");
        }
    }
}
