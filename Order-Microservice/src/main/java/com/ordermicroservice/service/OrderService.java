package com.ordermicroservice.service;

import java.util.List;

import com.ordermicroservice.dto.OrderDTO;
import com.ordermicroservice.entity.Orders;
import com.ordermicroservice.entity.TransactionDetails;
import com.ordermicroservice.enums.OrderStatus;
import com.ordermicroservice.exceptions.OrderNotFoundException;

public interface OrderService 
{

	Orders placeOrder(OrderDTO orderDTO);
	
	Orders getOrderById(Long orderId) throws OrderNotFoundException;
	
	List<Orders> getOrdersByUserId(Long userId);
	
	List<Orders> getAllOrders();
	
	public TransactionDetails createtransaction(Double amount);
	void updateOrderStatus(Long orderId, OrderStatus status) throws OrderNotFoundException;

}
