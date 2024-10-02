package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.dtos.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    ProductDTO findById(Long id);

    List<ProductDTO> findAll();

    void delete(Long id);
}
