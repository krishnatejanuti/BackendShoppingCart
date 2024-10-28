package com.ordermicroservice.dto;

import java.util.HashSet;
import java.util.Set;

import com.ordermicroservice.enums.CartStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO 
{

	    private Long cartId;
	    
	    private Long userId; // Assuming each cart is associated with a customer...FK dor the id in the customer table
	    
	    private double totalAmount;
	    
	    private Set<CartItemDTO> items = new HashSet<>();

		private CartStatus status;
		
		@Override
		public String toString() 
		{
			return "CartDTO [cartId=" + cartId + ", userId=" + userId + ", totalAmount=" + totalAmount + ", items="
					+ items + ", status=" + status + "]";
		}
	
}
