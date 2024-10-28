package com.cartmicroservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cartmicroservice.client.ProductClient;
import com.cartmicroservice.client.UserClient;
import com.cartmicroservice.dto.ProductDTO;
import com.cartmicroservice.dto.UserDTO;
import com.cartmicroservice.entity.Cart;
import com.cartmicroservice.entity.CartItem;
import com.cartmicroservice.exception.InvalidUserException;
import com.cartmicroservice.repository.CartItemRepository;
import com.cartmicroservice.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService
{
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	ProductClient productClient;
	
	@Autowired
	UserClient userClient;

	@Override
	public Cart createCart(Long userId,CartItem cartItem) throws InvalidUserException, Exception 
	{
		UserDTO userDTO = userClient.getByUserId(userId);
		if(userDTO==null)
		{
			throw new InvalidUserException("User with ID " + userId + " not found.");
		}
		
		Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
		if (cart.getCartId() == null) {
	        cart.setUserId(userId);
	        cart.setTotalAmount(0.0);
	    }

		
		ResponseEntity<ProductDTO> productDTO = productClient.getProductById(cartItem.getProductId());
		
		if(!productDTO.getStatusCode().is2xxSuccessful() || productDTO.getBody() == null)
		{
			throw new Exception("Product with ID " + cartItem.getProductId() + " not found.");
		}
		
		
		ProductDTO product = productDTO.getBody();
		
		double originalPrice = product.getPrice();
		double discountPrice = product.getDiscount();
		double updatedPrice = (originalPrice - discountPrice);
		
		cartItem.setPrice(updatedPrice);
		cartItem.setImageUrl(product.getImageUrl());
		
		boolean productExists = false;
		for (CartItem existingItem : cart.getItems())
		{
			if(existingItem.getProductId().equals(cartItem.getProductId()))
			{
				existingItem.setQuantity(existingItem.getQuantity()+cartItem.getQuantity());
				productExists = true;
				
				cart.setTotalAmount(cart.getTotalAmount()+(updatedPrice*cartItem.getQuantity()));
				break;
			}
		}
		
		if(!productExists)
		{
			cartItem.setCart(cart);
			cart.getItems().add(cartItem);
			
			double totalAmount = updatedPrice*cartItem.getQuantity();
			cart.setTotalAmount(cart.getTotalAmount()+totalAmount);
			
		}
		cartRepository.save(cart);
		return cart;
		

	}

	@Override
	public Optional<Cart> getCartByUserId(Long userId)
	{
		Optional<Cart> cart = cartRepository.findByUserId(userId);
		return cart;
	}

//	@Override
//	public Cart addItemToCart(Long userId, CartItem cartItem) 
//	{
//		Cart cart = cartRepository.findByUserId(userId).orElse(null);
//		
//		CartItem item = new CartItem();
//		cart.addItem(item);
//		
//		return cartRepository.save(cart);
//	}

	@Override
	public Cart removeItemFromCart(Long userId, Long productId) 
	{
//		Cart cart = cartRepository.findByUserId(userId).orElseThrow(()-> new RuntimeException("Cart not found for user id :"+userId));
//		
//		cart.getItems().removeIf(item -> item.getProductId().equals(productId));
//		
//		return cartRepository.save(cart);
		
		Cart cart = cartRepository.findByUserId(userId)
	            .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));
		
		 CartItem itemToRemove = cart.getItems().stream()
		            .filter(item -> item.getProductId().equals(productId))
		            .findFirst()
		            .orElseThrow(() -> new RuntimeException("Product not found in cart for product id: " + productId));
		 
		 double itemTotalPrice = itemToRemove.getPrice() * itemToRemove.getQuantity();
		    cart.setTotalAmount(cart.getTotalAmount() - itemTotalPrice);
		    
		    cart.getItems().removeIf(item -> item.getProductId().equals(productId));
		    
		    return cartRepository.save(cart);
	}

	@Override
	public void clearCart(Long userId)
	{
//		Cart cart = cartRepository.findByUserId(userId).orElseThrow(()-> new RuntimeException("Cart not found for user"+userId) );
//		cart.getItems().clear();
//		cartRepository.save(cart);
		
		Cart cart = cartRepository.findByUserId(userId)
		        .orElseThrow(() -> new RuntimeException("Cart not found for user " + userId));
		
		cart.getItems().clear();
		cart.setTotalAmount(0.0);
		
		cartRepository.save(cart);

	    System.out.println("Cart items cleared and total price reset to 0 for user " + userId);
	}

	@Override
	public List<CartItem> getCartItems(Long userId) 
	{
		List<CartItem> cartItemList = cartRepository.findByUserId(userId).map(Cart::getItems).orElseThrow(()->new RuntimeException("Cart not found for user"+userId)).stream().toList();
		return cartItemList;
	}

	@Override
	public Optional<Cart> getCartById(Long cartId) 
	{
		Optional<Cart> cartById = cartRepository.findById(cartId);
		cartById.get().getItems();
		
		return cartById;
	}

	@Override
	public List<Cart> getAllCarts() 
	{
		List<Cart> cartList = cartRepository.findAll();
		return cartList;
	}

	@Override
	public Cart updateCartItemsQuantity(Long userId,Long productId, CartItem updatedCartItem) throws InvalidUserException, Exception
	{
		UserDTO userDTO = userClient.getByUserId(userId);
	    if (userDTO == null) {
	        throw new InvalidUserException("User with ID " + userId + " not found.");
	    }

	    // Fetch the cart for the user
	    Cart cart = cartRepository.findByUserId(userId)
	                .orElseThrow(() -> new Exception("Cart not found for user ID " + userId));

	    // Fetch the product details to get the current price and discount
	    ResponseEntity<ProductDTO> productDTO = productClient.getProductById(productId);
	    if (!productDTO.getStatusCode().is2xxSuccessful() || productDTO.getBody() == null) {
	        throw new Exception("Product with ID " + productId + " not found.");
	    }

	    ProductDTO product = productDTO.getBody();
	    double originalPrice = product.getPrice();
	    double discountPrice = product.getDiscount();
	    double updatedPrice = originalPrice - discountPrice; // Calculating updated price

	    CartItem cartItemToUpdate = null;

	    // Find the specific product in the cart
	    for (CartItem existingItem : cart.getItems()) {
	        if (existingItem.getProductId().equals(productId)) {
	            cartItemToUpdate = existingItem;
	            break;
	        }
	    }

	    if (cartItemToUpdate == null) {
	        throw new Exception("Product with ID " + productId + " not found in the cart.");
	    }

	    // Update logic for the product's quantity
	    int oldQuantity = cartItemToUpdate.getQuantity();
	    int newQuantity = updatedCartItem.getQuantity();
	    if (newQuantity <= 0) {
	        // Remove the item if the quantity is zero or less
	        cart.getItems().remove(cartItemToUpdate);
	        cart.setTotalAmount(cart.getTotalAmount() - (cartItemToUpdate.getPrice() * oldQuantity));
	    } else {
	        // Adjust the total amount based on quantity change
	        cart.setTotalAmount(cart.getTotalAmount() - (cartItemToUpdate.getPrice() * oldQuantity)); // Subtract the old price
	        cartItemToUpdate.setQuantity(newQuantity); // Update the quantity
	        cartItemToUpdate.setPrice(updatedPrice); // Update price if needed
	        cart.setTotalAmount(cart.getTotalAmount() + (updatedPrice * newQuantity)); // Add the new total price
	    }

	    // Save the updated cart
	    cartRepository.save(cart);

	    return cart;
	}
}
