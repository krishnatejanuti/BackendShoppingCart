package com.ordermicroservice.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO 
{
	
@Override
	public String toString() {
		return "CartItemDTO [productId=" + productId + ", quantity=" + quantity + ", price=" + price + "]";
	}

	//    private Long cartitemId;
    private Long productId;
    private int quantity;
    private double price;

	@Override
	public int hashCode() 
	{
		return Objects.hash(productId);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItemDTO other = (CartItemDTO) obj;
		return Objects.equals(productId, other.productId);
	}
    

}
