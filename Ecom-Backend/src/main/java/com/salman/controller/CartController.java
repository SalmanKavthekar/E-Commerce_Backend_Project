package com.salman.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salman.payload.CartDto;
import com.salman.payload.ItemRequest;
import com.salman.service.CartService;
@RestController
@RequestMapping("/cart")
public class CartController {

	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/addCart")
	public ResponseEntity<CartDto> addToCart(@RequestBody ItemRequest itemRequest, Principal p){
		System.out.println(p.getName());
		CartDto addItem=cartService.addItem(itemRequest, p.getName());
		
		return new ResponseEntity<CartDto>(addItem, HttpStatus.OK);
	}
	
	//create method for getting cart
	@GetMapping("/getAll")
	public ResponseEntity<CartDto> getAllCart(Principal p){
		CartDto allCart=cartService.getAllCart(p.getName());
		return new ResponseEntity<CartDto>(allCart, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getCartById/{cartId}")
	public ResponseEntity<CartDto> getCartById(@PathVariable Integer cartId){
		CartDto cart =cartService.getCartById(cartId);
		
		return new ResponseEntity<CartDto>(cart,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCartItem/{pid}")
	public ResponseEntity<CartDto> removeItemFromCart(@PathVariable Integer pid, Principal p){
		CartDto remove=cartService.removeCartItemFromCart(p.getName(), pid);
		return new ResponseEntity<CartDto>(remove, HttpStatus.UPGRADE_REQUIRED);
	}
	
	
	
	
	
	
	
	
	
	
}
