package com.ordermicroservice.dto;

import java.time.LocalDateTime;

import com.ordermicroservice.enums.OrderStatus;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class OrderDTO 
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long cartId;
	private Long userId;
	private String utrNumber;
	private String deliveryAddress;
	private OrderStatus status;
	private LocalDateTime orderDate;
	
	

}
