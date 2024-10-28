package com.productmicroservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productmicroservice.dto.ProductDTO;
import com.productmicroservice.service.ProductService;


@RestController
@RequestMapping("/admin/product")
public class AdminProductController 
{

	@Autowired
	ProductService productService;
	
	@PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO)
    {
		ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
	
	@GetMapping("/getAllProducts")
	 public ResponseEntity<List<ProductDTO>> getAllProducts()
	 {
		 List<ProductDTO> productDTO = productService.getAllProducts();
		 return new ResponseEntity<>(productDTO, HttpStatus.OK);
	 }
	 
	 
	 @PutMapping("updateById/{productId}")
	 public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,@RequestBody ProductDTO productDTO)
	 {
		 Optional<ProductDTO> updatedProduct = productService.updateProduct(productId, productDTO);
	        return updatedProduct.map(ResponseEntity::ok)
	                             .orElseGet(() -> ResponseEntity.notFound().build());
	 }
	 
	 @DeleteMapping("deleteById/{productId}")
	    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long productId) 
	 {
	        boolean isDeleted = productService.deleteProduct(productId);
	        return isDeleted ? ResponseEntity.noContent().build()
	                         : ResponseEntity.notFound().build();
	 }
}

