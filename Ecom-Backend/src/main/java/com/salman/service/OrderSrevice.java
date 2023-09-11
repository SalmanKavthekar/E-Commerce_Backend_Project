package com.salman.service;

import com.salman.payload.OrderDto;
import com.salman.payload.OrderRequest;
import com.salman.payload.OrderResponse;

public interface OrderSrevice {
	
	public OrderDto orderCreate(OrderRequest request, String username);
	
	public void CancelOrder(int orderId);
	
	public OrderDto findById(int orderId);
	
	public OrderResponse findAllOrders(int pageNumber, int pageSize);

}
