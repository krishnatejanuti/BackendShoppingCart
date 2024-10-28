package com.cartmicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cartmicroservice.dto.ProductDTO;

@FeignClient(name = "product-microservice")
public interface ProductClient 
{
	@GetMapping("/product/getById/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId);
}
