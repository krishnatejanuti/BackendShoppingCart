package com.productmicroservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productmicroservice.dto.ProductDTO;
import com.productmicroservice.entity.Product;
import com.productmicroservice.service.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController
{
	@Autowired
	ProductService productService;
	
	 @GetMapping("/getById/{productId}")
	 public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId)
	 {
		 Optional<ProductDTO> productDTO = productService.getProductById(productId);
	        return productDTO.map(ResponseEntity::ok)
	                         .orElseGet(() -> ResponseEntity.notFound().build());
	 }
	 
	 @GetMapping("/getByProductName/{productName}")
	 public ResponseEntity<Product> getByProductName(@PathVariable String productName)
	 {
		 Optional<Product> product = productService.getByProductName(productName);
		 return product.map(ResponseEntity::ok)
				 .orElseGet(()-> ResponseEntity.notFound().build());
	 }
	 
	 
	 
}
