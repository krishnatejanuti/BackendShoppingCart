package com.cartmicroservice.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO 
{

    private Long productId;
    private String productName;
    private String productDescription;
    private String imageUrl;
    private Double price;
    private Double discount;
    private String brand;
    private Double rating;
    private Set<Long> categoryIds;
}
