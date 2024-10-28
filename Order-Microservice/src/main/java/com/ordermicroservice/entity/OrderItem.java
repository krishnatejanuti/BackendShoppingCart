package com.ordermicroservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderItem 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderitemId;

    private Long productId; // ID of the product being ordered
    
    private String imageUrl;
    
    private int quantity; // Quantity of the product

    private double price; // Price for the product (could be price per item or total for this item depending on your logic)

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // Foreign key linking to the orders
    private Orders orders;

    // Override equals and hashCode based on productId for logical equality
    @Override
    public int hashCode() 
    {
        return productId != null ? productId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrderItem other = (OrderItem) obj;
        return productId != null && productId.equals(other.productId);
    }

	@Override
	public String toString() 
	{
		return "OrderItem [orderitemId=" + orderitemId + ", productId=" + productId + ", quantity=" + quantity + ", price=" + price + "]";
	}
}
