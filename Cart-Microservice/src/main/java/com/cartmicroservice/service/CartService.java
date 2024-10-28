package com.cartmicroservice.service;

import java.util.List;
import java.util.Optional;

import com.cartmicroservice.entity.Cart;
import com.cartmicroservice.entity.CartItem;
import com.cartmicroservice.exception.InvalidUserException;

public interface CartService 
{
	Cart createCart(Long userId,CartItem cartItem) throws InvalidUserException, Exception;
	
    Optional<Cart> getCartByUserId(Long userId);
    
   // Cart addItemToCart(Long userId,CartItem cartItem);//even if the cart contains the item then just Increment the count of the product
    
    Cart removeItemFromCart(Long userId, Long productId);
    
    void clearCart(Long userId);
    
    List<CartItem> getCartItems(Long userId);
    
    Optional<Cart> getCartById(Long cartId);
    
    List<Cart> getAllCarts();
    
    Cart updateCartItemsQuantity(Long userId,Long productId,CartItem updatedCartItem) throws InvalidUserException, Exception;
}
