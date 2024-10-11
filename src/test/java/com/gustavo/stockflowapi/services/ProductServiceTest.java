package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.domain.product.Product;
import com.gustavo.stockflowapi.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1L);
        product.setName("Iphone 12");
        product.setDescription("Apple Iphone 12 250gb");
        product.setPrice(BigDecimal.valueOf(5000.00));
        product.setQuantity(10);
    }
}
