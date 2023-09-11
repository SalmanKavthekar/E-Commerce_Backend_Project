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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salman.payload.ApiResponse;
import com.salman.payload.OrderDto;
import com.salman.payload.OrderRequest;
import com.salman.payload.OrderResponse;
import com.salman.service.OrderSrevice;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderSrevice orderService;
	//create order 
	
	@PostMapping("/createOrder")
	public ResponseEntity<OrderDto> createOrder(@RequestBody  OrderRequest orderReq,Principal p) {
		String username=p.getName();
		OrderDto order=this.orderService.orderCreate(orderReq,username);
		return new ResponseEntity<OrderDto>(order,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/cancleOrder/{orderId}")
	public ResponseEntity<?> cancelOrderById(@PathVariable int orderId){
		this.orderService.CancelOrder(orderId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order deleted",true),HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto>findById(@PathVariable int orderId){
		OrderDto orderDto = this.orderService.findById(orderId);
		return new ResponseEntity<OrderDto>(orderDto,HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/findAllOrder")
	public OrderResponse findAllOrders(
			@RequestParam(defaultValue = "5",value = "pageSize") int pageSize 
			,@RequestParam(defaultValue = "0" ,value ="pageNumber" ) int pageNumber
			)
	
	{
		OrderResponse findAllOrders = this.orderService.findAllOrders(pageNumber, pageSize);
		
		
		return findAllOrders;
	}
	
	
	


}