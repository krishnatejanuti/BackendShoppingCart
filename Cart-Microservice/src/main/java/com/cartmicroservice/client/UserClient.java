package com.cartmicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cartmicroservice.dto.UserDTO;
import com.cartmicroservice.exception.UserNotFoundException;

@FeignClient(name = "user-microservice")
public interface UserClient 
{
	@GetMapping("user/getByUserId/{userId}")
	public UserDTO getByUserId(@PathVariable Long userId) throws UserNotFoundException;
}
