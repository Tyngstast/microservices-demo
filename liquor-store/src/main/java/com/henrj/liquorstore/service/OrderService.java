package com.henrj.liquorstore.service;

import com.henrj.liquorstore.data.Order;
import com.henrj.liquorstore.data.OrderDto;

import java.net.URI;
import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order getById(Long id);
    List<Order> getByCustomerEmail(String email);
    URI create(OrderDto orderDto);
}
