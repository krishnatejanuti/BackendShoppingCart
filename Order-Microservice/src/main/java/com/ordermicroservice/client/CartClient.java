package com.ordermicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ordermicroservice.dto.CartDTO;

@FeignClient(name = "cart-microservice")
public interface CartClient {

	@GetMapping("/cart/getById/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id);

	@DeleteMapping("cart/clear/{userId}")
	public ResponseEntity<Void> clearCart(@PathVariable Long userId);
}
