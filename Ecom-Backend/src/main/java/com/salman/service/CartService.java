package com.salman.service;

import com.salman.payload.CartDto;
import com.salman.payload.ItemRequest;

public interface CartService {

	public CartDto addItem(ItemRequest item, String username);
	public CartDto getAllCart(String email);
	public CartDto getCartById(Integer cartId); 
	public CartDto removeCartItemFromCart(String userName, Integer productId);
}
