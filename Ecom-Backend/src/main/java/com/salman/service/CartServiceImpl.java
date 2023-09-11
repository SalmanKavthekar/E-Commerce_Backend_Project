package com.salman.service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salman.exception.ResourceNotFoundException;
import com.salman.model.Cart;
import com.salman.model.CartItem;
import com.salman.model.Product;
import com.salman.model.User;
import com.salman.payload.CartDto;
import com.salman.payload.ItemRequest;
import com.salman.repository.CartRepo;
import com.salman.repository.ProductRepo;
import com.salman.repository.UserRepo;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	public CartDto addItem(ItemRequest item, String username) {
		int productId = item.getProductId();
		int quantity = item.getQuantity();
		// fetch user
		User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException());
		// fetch product
		Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException());

		// here we are checking product stock
		if (!product.isStock()) {
			new ResourceNotFoundException("Product Out of Stock");
		}

		// create cardItem with productId and quantity
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		double totalPrice = product.getProductPrize() * product.getProductQuantity();
		cartItem.setTotalPrice(totalPrice);

		// getting cart from user
		Cart cart = user.getCart();
		if (cart == null) {
			cart = new Cart();
			
			cart.setUser(user);
		}
		cartItem.setCart(cart);
		Set<CartItem> items = cart.getCartItems();
		//here we check product available in cart item or not
		//if product  is available then we increse quantity only 
		//else add new product in cart item
		
		 AtomicReference<Boolean> flag=new AtomicReference(false);
		 //we cant update any value in lambda function 
		 //altenate option using automicreference we will update  
		Set<CartItem> newProduct = items.stream().map((i)->{
			if(i.getProduct().getProductId()==product.getProductId()) {
				i.setQuantity(quantity);
				i.setTotalPrice(totalPrice);
				flag.set(true);
			}
			return i;
			
		}).collect(Collectors.toSet());
		
		if(flag.get()) {
			items.clear();
			items.addAll(newProduct);
		}else {
			cartItem.setCart(cart);
			items.add(cartItem);
		}
		
		Cart saveCart=cartRepo.save(cart);
		CartDto cartDto=mapper.map(saveCart, CartDto.class);

		return cartDto;
	}
	
	
	public CartDto getAllCart(String email) {
		//find user
		User user = this.userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		//find cart
	    Cart cart= this.cartRepo.findByUser(user).orElseThrow(()->new ResourceNotFoundException("There is no cart"));
		
		return mapper.map(cart, CartDto.class);
	}
	
	
	//get cart by cart id
	public CartDto getCartById(Integer cartId) {
//		User user=userRepo.findByemail(username).orElseThrow(()->new ResourceNotFoundException());
	    Cart cart = this.cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart not Found"));
		
		return mapper.map(cart, CartDto.class);
	}
	
	
	public CartDto removeCartItemFromCart(String userName, Integer productId) {
		User user=this.userRepo.findByEmail(userName).orElseThrow(()->new ResourceNotFoundException("User Not found"));
		Cart cart=user.getCart();
		Set<CartItem> items=cart.getCartItems();
		
		boolean removeIf =items.removeIf((i)->i.getProduct().getProductId()==productId);
		
		Cart save=cartRepo.save(cart);
		
		return mapper.map(save, CartDto.class);
	}
	
	
	
	
	
}










