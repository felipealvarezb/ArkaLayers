package com.example.ArkaLayers.service;

import com.example.ArkaLayers.entities.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

  List<Order> findOrdersByProductId(Long productId);

  List<Order> findOrdersByDateRange(LocalDate startDate, LocalDate endDate);

  List<Order> findOrdersByUserId(Long userId);

  Order createOrder(Order order);

  Order updateOrder(Long orderId, Order order);

  String deleteOrder(Long orderId);
}
