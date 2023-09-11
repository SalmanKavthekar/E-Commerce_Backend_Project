package com.salman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salman.model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer>{

}
