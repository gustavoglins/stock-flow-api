package com.stockflow.services;

import com.stockflow.dto.productDtos.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    ProductDTO findById(Long id);

    List<ProductDTO> listAll();

    void delete(Long id);
}
