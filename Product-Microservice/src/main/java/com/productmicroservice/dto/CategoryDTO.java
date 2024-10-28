package com.productmicroservice.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class CategoryDTO 
{

	@JsonProperty(access = Access.READ_ONLY)
    private Long categoryId;
	
    private String categoryName;
    private String description;
    
	@JsonProperty(access = Access.READ_ONLY)
    private Set<Long> productIds; 

   
    public CategoryDTO() 
    {
    	
    }
    
    public CategoryDTO(Long categoryId, String categoryName, String description, Set<Long> prodIds) {
    	super();
    	this.categoryId = categoryId;
    	this.categoryName = categoryName;
    	this.description = description;
    	this.productIds = prodIds;
    }

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(Set<Long> productIds) {
		this.productIds = productIds;
	}
    


	
    

}

