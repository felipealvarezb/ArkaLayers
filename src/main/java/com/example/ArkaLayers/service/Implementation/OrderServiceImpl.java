package com.example.ArkaLayers.service.Implementation;

import com.example.ArkaLayers.entities.Order;
import com.example.ArkaLayers.repositories.OrderRepository;
import com.example.ArkaLayers.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public List<Order> findOrdersByProductId(Long productId) {
    List<Order> orders = orderRepository.findOrdersByProductId(productId);
    return orders;
  }

  @Override
  public List<Order> findOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
    List<Order> orders = orderRepository.findOrdersInDateRange(startDate, endDate);
    return orders;
  }

  @Override
  public List<Order> findOrdersByUserId(Long userId) {
    List<Order> orders = orderRepository.findOrdersByUserId(userId);
    return orders;
  }

  @Override
  public Order createOrder(Order order) {
    Order savedOrder = orderRepository.save(order);
    return savedOrder;
  }

  @Override
  public Order updateOrder(Long orderId, Order order) {
    return null;
  }

  @Override
  public String deleteOrder(Long orderId) {
    Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    orderRepository.delete(existingOrder);
    return "Order deleted successfully";
  }
}
