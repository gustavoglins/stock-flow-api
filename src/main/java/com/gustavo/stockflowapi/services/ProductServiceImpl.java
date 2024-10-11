package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.domain.product.Product;
import com.gustavo.stockflowapi.dtos.ProductDTO;
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

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        return new ProductDTO(repository.save(new Product(productDTO)));
    }

    @Override
    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        Optional<Product> optionalProduct = repository.findById(productDTO.id());
        if (optionalProduct.isPresent()) {
            Product selectedProduct = optionalProduct.get();

            selectedProduct.setName(productDTO.name());
            selectedProduct.setDescription(productDTO.description());
            selectedProduct.setPrice(productDTO.price());
            selectedProduct.setQuantity(productDTO.quantity());

            return new ProductDTO(repository.save(selectedProduct));
        } else {
                throw new RuntimeException("Product not found"); //TODO
        }
    }

    @Override
    public ProductDTO findById(Long id) {
        return new ProductDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"))); //TODO
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> productsList = repository.findAll();
        return productsList
                .stream()
                .map(ProductDTO::new)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found"); //TODO
        }
    }

}
