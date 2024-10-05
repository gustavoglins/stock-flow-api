package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.dtos.ProductDTO;
import com.gustavo.stockflowapi.exceptions.DuplicateProductException;
import com.gustavo.stockflowapi.exceptions.InvalidProductDataException;
import com.gustavo.stockflowapi.exceptions.ProductNotFoundException;
import com.gustavo.stockflowapi.model.Product;
import com.gustavo.stockflowapi.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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

    // ProductService CREATE method tests START

    @Test
    @Order(1)
    @DisplayName("test for given Product Object when Create Product then Return Product Object")
    void givenProductObject_whenCreateProduct_thenReturnProductObject() {
        when(repository.save(any(Product.class))).thenReturn(product);

        ProductDTO createdProduct = service.create(new ProductDTO(product));

        assertNotNull(createdProduct);
        assertEquals("Iphone 12", product.getName());
        assertEquals("Apple Iphone 12 250gb", product.getDescription());
        assertEquals(BigDecimal.valueOf(5000.00), product.getPrice());
        assertEquals(10, product.getQuantity());
    }

    @Test
    @Order(2)
    @DisplayName("test for given Duplicate Product Name when Create Product then Throw Duplicate Product Exception")
    void givenDuplicateProductName_whenCreateProduct_thenThrowDuplicateProductException() {
        when(repository.save(any(Product.class))).thenThrow(DuplicateProductException.class);

        assertThrows(DuplicateProductException.class, () -> service.create(new ProductDTO(product)));

        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    @Order(3)
    @DisplayName("test for given Invalid Product Object when Create Product then Throws InvalidProductDataException")
    void givenInvalidProductObject_whenCreatePerson_thenThrowInvalidProductDataException() {
        when(repository.save(any(Product.class))).thenThrow(new InvalidProductDataException("Invalid product data"));

        ProductDTO invalidProductDTO = new ProductDTO(product);

        InvalidProductDataException exception = assertThrows(InvalidProductDataException.class, () -> service.create(invalidProductDTO));

        assertEquals("Invalid product data", exception.getMessage());

        verify(repository, times(1)).save(any(Product.class));
    }

    // ProductService CRETE method tests END

    // ProductService UPDATE method tests START

    @Test
    @Order(4)
    @DisplayName("test for given Product Object when Update Product then Return Updated Product Object")
    void givenProductObject_whenUpdateProduct_thenReturnUpdatedProductObject() {
        // Arrange
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);

        product.setName("Iphone 12 UPDATED");
        product.setDescription("Apple Iphone 12 250gb updated");
        product.setPrice(BigDecimal.valueOf(6000.00));
        product.setQuantity(0);

        ProductDTO updatedProduct = new ProductDTO(product);

        // Act
        service.update(updatedProduct);

        // Assert
        assertAll(
                () -> assertNotNull(updatedProduct),
                () -> assertEquals("Iphone 12 UPDATED", updatedProduct.name()),
                () -> assertEquals("Apple Iphone 12 250gb updated", updatedProduct.description()),
                () -> assertEquals(BigDecimal.valueOf(6000.00), updatedProduct.price()),
                () -> assertEquals(0, updatedProduct.quantity())
        );
    }


    @Test
    @Order(5)
    @DisplayName("test for given Not Existing Product when Updated Product then Throw ProductNotFoundException")
    void givenNotExistingProduct_whenUpdateProduct_thenThrowProductNotFoundException() {
        // Arrange
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> service.update(new ProductDTO(product)));

        assertAll(
                () -> assertEquals("Product not found", exception.getMessage())
        );
    }

    // ProductService UPDATE method tests END

    // ProductService FIND BY ID method tests START

    @Test
    @Order(6)
    @DisplayName("test for given Product ID when FindById then Return Product Object")
    void givenProductId_whenFindById_thenReturnProductObject() {
        given(repository.findById(anyLong())).willReturn(Optional.of(product));

        ProductDTO foundProduct = service.findById(anyLong());

        assertAll(
                () -> assertNotNull(foundProduct),
                () -> assertEquals(new ProductDTO(product), foundProduct)
        );
    }

    @Test
    @Order(7)
    @DisplayName("test for given Not Existing Product ID when FindById then Throw ProductNotFoundException")
    void givenNotExistingProductId_whenFindById_thenThrowProductNotFoundException() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> service.findById(anyLong()));

        assertAll(
                () -> assertEquals("Product not found", exception.getMessage())
        );
    }

    // ProductService FIND BY ID method tests END

    // ProductService FIND ALL method tests START

    @Test
    @Order(8)
    @DisplayName("test for when FindAll then Return All Products List")
    void whenFindAll_thenReturnAllProductsList() { // TODO
    }

    @Test
    @Order(9)
    @DisplayName("test for when FindAll and No Products Exist then Return Empty List")
    void whenFindAll_andNoProductsExist_thenReturnEmptyList() { // TODO
    }

    // ProductService FIND ALL method tests END

    // ProductService DELETE method tests START

    @Test
    @Order(10)
    @DisplayName("test for given Product ID when Delete Product then do Nothing")
    void givenProductId_whenDeleteProduct_thenDoNothing() {
        given(repository.findById(anyLong())).willReturn(Optional.of(product));
        willDoNothing().given(repository).deleteById(anyLong());

        service.delete(product.getId());

        verify(repository, times(1)).deleteById(product.getId());
    }

    @Test
    @Order(11)
    @DisplayName("test for given Not Existing Product ID then Delete Product then Throw Product Not Found Exception")
    void givenNotExistingProductId_whenDeleteProduct_thenThrowProductNotFoundException() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> service.delete(anyLong()));

        assertAll(
                () -> assertEquals("Product not found", exception.getMessage())
        );
    }

    // ProductService DELETE method tests END
}
