package com.example.ArkaLayers.service;

import com.example.ArkaLayers.entities.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

  List<Order> findOrdersByProductId(Long productId);

  List<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

  List<Order> findOrdersByUserId(Long userId);

  Order createOrder(Long userId);

  Order updateOrder(Long orderId, Order order);

  String deleteOrder(Long orderId);
}
