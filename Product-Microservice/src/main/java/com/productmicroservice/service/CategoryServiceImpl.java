package com.productmicroservice.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productmicroservice.dto.CategoryDTO;
import com.productmicroservice.entity.Category;
import com.productmicroservice.entity.Product;
import com.productmicroservice.repository.CategoryRepository;
import com.productmicroservice.repository.ProductRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService 
{
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ProductRepository productRepository;

   
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) 
    {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        
//        Set<Product> products = categoryDTO.getProductIds()
//        		.stream()
//        		.map(productId -> productRepository
//        				.findById(productId)
//        				.orElseThrow(()-> new RuntimeException("Product not found with ID: " + productId)))
//        		.collect(Collectors.toSet());
//        category.setProducts(products);
//        
//        products.forEach(product->{
//        	product.getCategories().add(category);
//        	productRepository.save(product);
//        });
//        
        
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }


    @Override
    public Optional<CategoryDTO> getCategoryById(Long categoryId) 
    {
        return categoryRepository.findById(categoryId).map(this::convertToDTO);
    }

    @Override
    public List<CategoryDTO> getAllCategories() 
    {
        List<Category> allCategories = categoryRepository.findAll();

        return allCategories.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
    }


    
    @Override
    public Optional<CategoryDTO> updateCategory(Long categoryId, CategoryDTO categoryDTO) 
    {
    	if (categoryRepository.existsById(categoryId)) 
    	{
            Category category = categoryRepository.findById(categoryId).get();
            
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setDescription(categoryDTO.getDescription());
            
            if (categoryDTO.getProductIds() != null && !categoryDTO.getProductIds().isEmpty())
            {
            	 Set<Product> products = categoryDTO.getProductIds()
                 		.stream()
                 		.map(productId -> productRepository
                 				.findById(productId)
                 				.orElseThrow(()-> new RuntimeException("Product not found with ID: " + productId)))
                 		.collect(Collectors.toSet());
            	 
                 category.setProducts(products);
                 
                 products.forEach(product -> {
                	 product.getCategories().add(category);
                	 productRepository.save(product);
                 });
            }
            Category updatedCategory = categoryRepository.save(category);
            return Optional.of(convertToDTO(updatedCategory));
        } else 
        {
            return Optional.empty();
        }
            
    }

  
    @Override
    public boolean deleteCategory(Long categoryId) 
    {
    	if (categoryRepository.existsById(categoryId)) 
    	{
            Category category = categoryRepository.findById(categoryId).get();
            category.getProducts().forEach(product -> {
                product.getCategories().remove(category);
                productRepository.save(product);
            });

            categoryRepository.deleteById(categoryId);
            return true;
        } else {
            return false;
        }
    }

     //Convert entity to DTO
    private CategoryDTO convertToDTO(Category category) 
    {
        Set<Long> prodIds = category.getProducts().stream()
                                       .map(Product::getProductId)
                                       .collect(Collectors.toSet());

        return new CategoryDTO(
            category.getCategoryId(),
            category.getCategoryName(),
            category.getDescription(),
            prodIds
        );
    }

    

}

