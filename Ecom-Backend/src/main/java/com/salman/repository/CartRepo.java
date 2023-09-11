package com.salman.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salman.model.Cart;
import com.salman.model.User;

public interface CartRepo extends JpaRepository<Cart, Integer>{
	
	public Optional<Cart>findByUser(User user);
	

}
