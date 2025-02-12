package org.ecom.vpecom.service;

import org.ecom.vpecom.dto.ProductDTO;
import org.ecom.vpecom.dto.ProductResponseDTO;
import org.ecom.vpecom.model.Product;

public interface ProductService {
    public ProductDTO addProduct(Long categoryId, Product product);
    public ProductResponseDTO getAllProducts();
}
