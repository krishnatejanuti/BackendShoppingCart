package com.productmicroservice.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productmicroservice.dto.ProductDTO;
import com.productmicroservice.entity.Category;
import com.productmicroservice.entity.Product;
import com.productmicroservice.repository.CategoryRepository;
import com.productmicroservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService
{
	
	@Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) 
	{
		Product product = new Product();
		product.setProductName(productDTO.getProductName());
		product.setProductDescription(productDTO.getProductDescription());
		product.setImageUrl(productDTO.getImageUrl());
		product.setPrice(productDTO.getPrice());
		product.setDiscount(productDTO.getDiscount());
		product.setBrand(productDTO.getBrand());
		product.setRating(productDTO.getRating());

		Set<Category> categories = productDTO.getCategoryIds()
				.stream()
				.map(categoryId -> categoryRepository
						.findById(categoryId)
						.orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId)))
				.collect(Collectors.toSet());
		product.setCategories(categories);

		Product savedProduct = productRepository.save(product);
		return convertToDTO(savedProduct);
	}

	@Override
	public Optional<ProductDTO> getProductById(Long productId) 
	{
		Optional<ProductDTO> productDTO = productRepository.findById(productId).map(this::convertToDTO);
		return productDTO;
	}

	@Override
	public List<ProductDTO> getAllProducts() 
	{
		List<ProductDTO> productList = productRepository.findAll().stream()
														.map(this::convertToDTO)
														.collect(Collectors.toList());
		return productList;
	}

	@Override
	public Optional<ProductDTO> updateProduct(Long productId, ProductDTO productDTO)
	{
		 if (productRepository.existsById(productId)) 
		 {
				Product product = productRepository.findById(productId).get();
				product.setProductName(productDTO.getProductName());
				product.setProductDescription(productDTO.getProductDescription());
				product.setImageUrl(productDTO.getImageUrl());
				product.setPrice(productDTO.getPrice());
				product.setDiscount(productDTO.getDiscount());
				product.setBrand(productDTO.getBrand());
				product.setRating(productDTO.getRating());

				Set<Category> categories = productDTO.getCategoryIds().stream()
						.map(categoryId -> categoryRepository.findById(categoryId)
								.orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId)))
						.collect(Collectors.toSet());
				product.setCategories(categories);
				Product updatedProduct = productRepository.save(product);
				return Optional.of(convertToDTO(updatedProduct));
		}
		 else
		 {
			 return Optional.empty();
		 }
	}

	@Override
	public boolean deleteProduct(Long productId) 
	{
		if (productRepository.existsById(productId)) 
		{
            productRepository.deleteById(productId);
            return true;
        } 
		else 
        {
            return false;
        }
	}
	
	@Override
	public Optional<Product> getByProductName(String productName) 
	{
		Optional<Product> product = productRepository.findByProductName(productName);
		return product;
	}
	
	 private ProductDTO convertToDTO(Product product) 
	 {
	        Set<Long> categoryIds = product.getCategories().stream()
	                                        .map(Category::getCategoryId)
	                                        .collect(Collectors.toSet());

	        return new ProductDTO(
	            product.getProductId(),
	            product.getProductName(),
	            product.getProductDescription(),
	            product.getImageUrl(),
	            product.getPrice(),
	            product.getDiscount(),
	            product.getBrand(),
	            product.getRating(),
	            categoryIds
	        );
	    }

	

}
