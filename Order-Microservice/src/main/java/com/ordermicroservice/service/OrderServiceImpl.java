package com.ordermicroservice.service;

import java.time.LocalDateTime;
import java.util.HashSet;

 
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermicroservice.client.CartClient;
import com.ordermicroservice.client.ProductClient;
import com.ordermicroservice.dto.CartDTO;
import com.ordermicroservice.dto.CartItemDTO;
import com.ordermicroservice.dto.OrderDTO;
import com.ordermicroservice.dto.ProductDTO;

import com.ordermicroservice.entity.OrderItem;
import com.ordermicroservice.entity.Orders;
import com.ordermicroservice.entity.TransactionDetails;
import com.ordermicroservice.enums.OrderStatus;
import com.ordermicroservice.exceptions.OrderNotFoundException;
import com.ordermicroservice.repository.OrderRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService 
{
	
	
	private static final String KEY="rzp_test_eNK0URqmW6qyWf";
	private static final String KEY_SECRET="m8fmO1yB09lavZ2kuROcbJHQ";
	private static final String CURRENCY="INR";
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CartClient cartClient;
	
	@Autowired
	ProductClient productClient;
			
	@Autowired
	ModelMapper mapper;

	@Override
	public Orders placeOrder(OrderDTO orderDTO) 
	{

		CartDTO cartById = cartClient.getCartById(orderDTO.getCartId()).getBody();
		
		log.info("cart : {}",cartById.toString());
				
		Set<OrderItem> orderItems = new HashSet<>();
		
		for(CartItemDTO each : cartById.getItems())
		{
			 ProductDTO productDTO = productClient.getProductById(each.getProductId()).getBody();
			
			OrderItem orderItem=mapper.map(each, OrderItem.class);
			
			orderItem.setImageUrl(productDTO.getImageUrl());
			
			orderItems.add(orderItem);
			
		}
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		Orders order = new Orders(cartById.getUserId(),cartById.getTotalAmount(),OrderStatus.ARRIVING,currentDateTime);
		
		orderItems.forEach(order::addItem);
		
		log.info("Orders Items: {}",orderItems.toString());
		
//		cartById.getItems().clear(); 
//		cartById.setTotalAmount(0.0);
		cartClient.clearCart(cartById.getUserId());
		
		return orderRepository.save(order);
	}

	@Override
	public Orders getOrderById(Long orderId) throws OrderNotFoundException 
	{
		 return orderRepository.findById(orderId)
	                .orElseThrow(() -> new OrderNotFoundException("Orders with ID " + orderId + " not found"));
	}

	@Override
	public List<Orders> getOrdersByUserId(Long userId) 
	{
		 List<Orders> orderList = orderRepository.findByUserId(userId);
		 orderList.sort((o1,o2)-> Long.compare(o2.getOrderId(), o1.getOrderId()));
		 return orderList;
	}

	@Override
	public void updateOrderStatus(Long orderId, OrderStatus status) throws OrderNotFoundException
	{
		Optional<Orders> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) 
        {
            Orders order = optionalOrder.get();
            order.setStatus(status);
            orderRepository.save(order);
        } else 
        {
            throw new OrderNotFoundException("Orders not found with id: " + orderId);
        }
    }

	@Override
	public List<Orders> getAllOrders() 
	{
		List<Orders> orderList = orderRepository.findAll();
		return orderList;
	}

	@Override
	public TransactionDetails createtransaction(Double amount) {
		try {
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("amount", (amount * 100));
		jsonobject.put("currency",CURRENCY);
		
		RazorpayClient razorpayclient=new RazorpayClient(KEY, KEY_SECRET);
	
		Order or= razorpayclient.orders.create(jsonobject);
	
		
	  return prepareTranscationdetails(or);
		
	
	
	} catch (Exception e) {
		
		System.out.println(e.getMessage());
	}
	return null;
	}
	
	private TransactionDetails prepareTranscationdetails(Order ord) {
		String orId=ord.get("id");
		String currency=ord.get("currency");
		Integer amount = ord.get("amount");
		
		
		TransactionDetails transactiondetails = new TransactionDetails(orId,currency,amount,KEY);
	
		return transactiondetails;

}
}
