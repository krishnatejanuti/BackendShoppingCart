package com.productmicroservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productmicroservice.dto.CategoryDTO;
import com.productmicroservice.service.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController 
{
	@Autowired
	CategoryService categoryService;
	
	
	
	@GetMapping("getById/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) 
	{
		Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(categoryId);
		return categoryDTO.map(ResponseEntity::ok)
				.orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	 
	 
	 
}
