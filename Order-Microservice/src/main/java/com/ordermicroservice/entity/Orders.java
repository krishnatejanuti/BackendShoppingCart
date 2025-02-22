package com.ordermicroservice.entity;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ordermicroservice.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orders 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long userId; // ID of the user who placed the order
    private double totalAmount; // Total amount for the order

  
    @Enumerated(EnumType.STRING)
    private OrderStatus status ;// Status of the order (e.g., PENDING, PAID, SHIPPED)

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm",timezone = "UTC")
    private LocalDateTime orderDate;
    
    @OneToMany(mappedBy = "orders", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
    private List<OrderItem> items =new LinkedList<>();
    //private Set<OrderItem> items = new HashSet<>();

    // Utility methods
    public void addItem(OrderItem item) 
    {
        items.add(0,item);
        item.setOrders(this);
    }

    public void removeItem(OrderItem item) 
    {
        items.remove(item);
        items.set(0,null);
    }

	public Orders(Long userId, double totalAmount, OrderStatus status,LocalDateTime orderDate) 
	{
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.status = status;
		this.orderDate= orderDate;
	}

	
}
