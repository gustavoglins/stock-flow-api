package com.stockflow.services;

import com.stockflow.dto.productDtos.ProductRequestDTO;
import com.stockflow.dto.productDtos.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO create(ProductRequestDTO productRequestDTO);

    ProductResponseDTO update(ProductRequestDTO productRequestDTO);

    ProductResponseDTO findById(Long id);

    List<ProductResponseDTO> listAll();

    void delete(Long id);
}
