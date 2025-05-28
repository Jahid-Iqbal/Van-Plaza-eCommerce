package org.ecom.vpecom.service;

import org.ecom.vpecom.dto.ProductDTO;
import org.ecom.vpecom.dto.ProductResponseDTO;
import org.ecom.vpecom.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    public ProductDTO addProduct(Long categoryId, ProductDTO productDto);
    public ProductResponseDTO getAllProducts();
    public ProductResponseDTO searchProductsByCategory(Long categoryId);
    public ProductResponseDTO searchProductByKeyword(String keyword);
    public ProductDTO updateProduct(Long id, ProductDTO productDto);
    public ProductDTO deleteProduct(Long productId);
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

}
