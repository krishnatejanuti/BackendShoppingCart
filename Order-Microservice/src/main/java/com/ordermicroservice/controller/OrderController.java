package com.ordermicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ordermicroservice.dto.OrderDTO;
import com.ordermicroservice.entity.Orders;
import com.ordermicroservice.entity.TransactionDetails;
import com.ordermicroservice.enums.OrderStatus;
import com.ordermicroservice.exceptions.OrderNotFoundException;
import com.ordermicroservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController 
{

	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	public ResponseEntity<Orders> placeOrder(@RequestBody OrderDTO orderDTO) 
	{
		Orders placedOrder = orderService.placeOrder(orderDTO);

		return ResponseEntity.status(201).body(placedOrder);
	}

	@GetMapping("getOrderById/{orderId}")
	public ResponseEntity<Orders> getOrderById(@PathVariable Long orderId) throws OrderNotFoundException {
		Orders orders = orderService.getOrderById(orderId);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/getOrderUserId/{userId}")
	public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
	}
	
	@PutMapping("/updateStatus/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) throws OrderNotFoundException 
	{
       orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Orders Status updated successfully");
    }
	
	@GetMapping("/getAllOrders")
	public ResponseEntity<List<Orders>> getAllOrders() 
	{
		return ResponseEntity.ok(orderService.getAllOrders());
	}
	
	@GetMapping("/createtransaction/{amount}")
	public TransactionDetails createTransacation(@PathVariable Double amount) {
		return orderService.createtransaction(amount);
	}

}
