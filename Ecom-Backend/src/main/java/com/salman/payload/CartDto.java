package com.salman.payload;

import java.util.HashSet;
import java.util.Set;

public class CartDto {

	private int cartId;

	private Set<CartItemDto> cartItems = new HashSet<>();

	private UserDto user;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Set<CartItemDto> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	
	
	
	
}
