package com.productmicroservice.service;

import java.util.List;
import java.util.Optional;

import com.productmicroservice.dto.CategoryDTO;

public interface CategoryService 
{
	CategoryDTO createCategory(CategoryDTO categoryDTO);

	Optional<CategoryDTO> getCategoryById(Long categoryId);

	List<CategoryDTO> getAllCategories();

	Optional<CategoryDTO> updateCategory(Long categoryId, CategoryDTO categoryDTO);

	boolean deleteCategory(Long categoryId);
}
