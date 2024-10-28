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

import com.productmicroservice.dto.CategoryDTO;
import com.productmicroservice.service.CategoryService;


@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController 
{
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) 
	{
		CategoryDTO dto = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(dto,HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() 
	{
		List<CategoryDTO> categoryDTO = categoryService.getAllCategories();
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}
	
	@PutMapping("updateBy/{categoryId}")
	 public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,@RequestBody CategoryDTO categoryDTO) 
	 {
		 Optional<CategoryDTO> updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
		 return updatedCategory.map(ResponseEntity::ok)
				 .orElseGet(()-> ResponseEntity.notFound().build());
	 }
	 
	 @DeleteMapping("deleteById/{categoryId}")
	 public ResponseEntity<Boolean> deleteCategory(@PathVariable Long categoryId)
	 {
		 boolean isDeleted = categoryService.deleteCategory(categoryId);
		 return isDeleted ? ResponseEntity.noContent().build()
				 			: ResponseEntity.notFound().build();
	 }
}
