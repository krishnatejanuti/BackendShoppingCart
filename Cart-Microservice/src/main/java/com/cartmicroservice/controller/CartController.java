package com.cartmicroservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartmicroservice.entity.Cart;
import com.cartmicroservice.entity.CartItem;
import com.cartmicroservice.exception.InvalidUserException;
import com.cartmicroservice.service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController 
{
	@Autowired
	CartService cartService;

	// This will just create the empty cart for the user
	@PostMapping("/create/{userId}")
	public ResponseEntity<Cart> createCart(@PathVariable Long userId, @RequestBody CartItem cartItem)
			throws InvalidUserException, Exception 
	{
		Cart cart = cartService.createCart(userId, cartItem);
		return new ResponseEntity<>(cart, HttpStatus.OK);

	}

	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<Optional<Cart>> getCartByUserId(@PathVariable Long userId) 
	{
		Optional<Cart> cart = cartService.getCartByUserId(userId);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	// this will find the cart based on the userId ...then it will add the item in
	// cart ..and if already present we will increase the quantity of the Cart_item
	// by One

//	 @PostMapping("/addItem/{userId}/add")
//	public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId,@RequestBody CartItem cartItem)
//	{
//		 Cart cart = cartService.addItemToCart(userId, cartItem);
//		 return new ResponseEntity<>(cart,HttpStatus.OK);
//	}

	@DeleteMapping("/remove/{userId}/{productId}")
	public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long userId, @PathVariable Long productId) 
	{
		Cart cart = cartService.removeItemFromCart(userId, productId);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@DeleteMapping("/clear/{userId}")
	public ResponseEntity<Void> clearCart(@PathVariable Long userId) 
	{
		cartService.clearCart(userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/itemsByUserId/{userId}")
	public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long userId) 
	{
		List<CartItem> itemList = cartService.getCartItems(userId);
		return new ResponseEntity<>(itemList, HttpStatus.OK);
	}

	@GetMapping("/getById/{cartId}")
	public ResponseEntity<Cart> getCartById(@PathVariable Long cartId)
	{
		return cartService.getCartById(cartId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/getAllCarts")
	public ResponseEntity<List<Cart>> getAllCarts() 
	{
		return ResponseEntity.ok(cartService.getAllCarts());
	}
	
	@PutMapping("updateCart/{userId}/{productId}")
	public ResponseEntity<Cart> updateCartItemsQuantity(@PathVariable Long userId,@PathVariable Long productId,@RequestBody CartItem updatedCartItem) throws InvalidUserException, Exception
	{
		Cart cart = cartService.updateCartItemsQuantity(userId,productId,updatedCartItem);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

}
