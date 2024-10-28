package com.productmicroservice.service;

import java.util.List;
import java.util.Optional;

import com.productmicroservice.dto.ProductDTO;
import com.productmicroservice.entity.Product;

public interface ProductService 
{
	ProductDTO createProduct(ProductDTO productDTO);

	Optional<ProductDTO> getProductById(Long productId);

	List<ProductDTO> getAllProducts();

	Optional<ProductDTO> updateProduct(Long productId, ProductDTO productDTO);

	boolean deleteProduct(Long productId);
	
	Optional<Product> getByProductName(String productName);
}
