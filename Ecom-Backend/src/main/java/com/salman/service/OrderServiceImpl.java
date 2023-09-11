package com.salman.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.salman.exception.ResourceNotFoundException;
import com.salman.model.Cart;
import com.salman.model.CartItem;
import com.salman.model.Order;
import com.salman.model.OrderItem;
import com.salman.model.User;
import com.salman.payload.OrderDto;
import com.salman.payload.OrderRequest;
import com.salman.payload.OrderResponse;
import com.salman.repository.CartRepo;
import com.salman.repository.OrderRepo;
import com.salman.repository.UserRepo;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderSrevice {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CartRepo cartRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private OrderRepo orderReop;

	// order Create method

	public OrderDto orderCreate(OrderRequest request, String username) {

		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User not found"));

		int cartId = request.getCartId();
		String orderAddress = request.getOrderAddress();
		// find cart
		Cart cart= this.cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
		// getting CartItem
		Set<CartItem> items = cart.getCartItems() ;
		Order order = new Order();
		

		AtomicReference<Double> totalOrderPrice = new AtomicReference<Double>(0.0);
		
		//CartItem map to OrderItem
		Set<OrderItem> orderitems = items.stream().map((cartItem) -> {
			OrderItem orderItem = new OrderItem();
			// set cartItem into OrderItem

			// set product in orderItem
			orderItem.setProduct(cartItem.getProduct());

			// set productQty in orderItem

			orderItem.setProductQuantity(cartItem.getQuantity());

			orderItem.setTotalProductprice(cartItem.getTotalPrice());
			orderItem.setOrder(order);

			totalOrderPrice.set(totalOrderPrice.get()+ orderItem.getTotalProductprice());
			int productId = orderItem.getProduct().getProductId();

			return orderItem;
		}).collect(Collectors.toSet());

		order.setBillingAddress(orderAddress);
		order.setOrderDelivered(null);
		order.setOrderStatus("CREATED");
		order.setPaymentStatus("NOT PAID");
		order.setUser(user);
		order.setOrderItem(orderitems);
		order.setOrderAmt(totalOrderPrice.get());
		order.setOrderCreateAt(new Date());
		Order save;
		if (order.getOrderAmt() > 0) {
			save = orderReop.save(order);
			cart.getCartItems().clear();
			cartRepo.save(cart);
			System.out.println("Hello");
		} else {
			System.out.println(order.getOrderAmt());
			throw new ResourceNotFoundException("Plz Add Cart First and place Order");
		}

		return this.modelMapper.map(save, OrderDto.class);
	}

	public void CancelOrder(int orderId) {
		Order order = this.orderReop.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not Found"));
		this.orderReop.delete(order);
	}

	public OrderDto findById(int orderId) {
		Order order = this.orderReop.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order not found"));
		return this.modelMapper.map(order, OrderDto.class);
	}

	// find All Product by page

	public OrderResponse findAllOrders(int pageNumber, int pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Order> findAll = this.orderReop.findAll(pageable);
		List<Order> content = findAll.getContent();

		// change order to orderDto
		List<OrderDto> collect = content.stream().map((each) -> this.modelMapper.map(each, OrderDto.class))
				.collect(Collectors.toList());
		OrderResponse response = new OrderResponse();
		response.setContent(collect);
		response.setPageNumber(findAll.getNumber());
		response.setLastPage(findAll.isLast());
		response.setPageSize(findAll.getSize());
		response.setTotalPage(findAll.getTotalPages());

		// getTotalElements return Long
		response.setTotalElemet(findAll.getTotalElements());

		return response;
	}

}