package com.cartmicroservice.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Cart 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
	
	private Long userId;
	
	private double totalAmount;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();
	
	//private CartStatus status;
	
	// Utility methods for managing cart items 
    public void addItem(CartItem item) 
    {
    	totalAmount=totalAmount+item.getPrice();
    	
    	for(CartItem eachItem : items)
    	{
    		if (eachItem.equals(item))
    		{
    			eachItem.setQuantity(eachItem.getQuantity()+1);
    			return;
    		}
    	}
    	
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) 
    {
    	totalAmount=totalAmount-item.getPrice();
        items.remove(item);
        item.setCart(null);
    }
	
}
