package com.example.ArkaLayers.service.Implementation;

import com.example.ArkaLayers.entities.*;
import com.example.ArkaLayers.repositories.CartDetailRepository;
import com.example.ArkaLayers.repositories.CartRepository;
import com.example.ArkaLayers.repositories.OrderRepository;
import com.example.ArkaLayers.repositories.UserRepository;
import com.example.ArkaLayers.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartDetailRepository cartDetailRepository;

  @Override
  public List<Order> findOrdersByProductId(Long productId) {
    List<Order> orders = orderRepository.findOrdersByProductId(productId);
    return orders;
  }

  @Override
  public List<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
    List<Order> orders = orderRepository.findOrdersInDateRange(startDate, endDate);
    return orders;
  }

  @Override
  public List<Order> findOrdersByUserId(Long userId) {
    List<Order> orders = orderRepository.findOrdersByUserId(userId);
    return orders;
  }

  @Override
  public Order createOrder(Long userId) {
    User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Cart userCart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("User cart not found"));

    List<CartDetail> cartDetails = userCart.getCartDetails();

    if (cartDetails.isEmpty()) {
      throw new IllegalStateException("Cart is empty");
    }

    Order order = new Order();
    order.setUser(existingUser);
    order.setStatus("Pending");
    order.setOrderDetails(new ArrayList<>());

    double totalOrder = 0.0;
    for (CartDetail cartDetail : cartDetails) {
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setProduct(cartDetail.getProduct());
      orderDetail.setOrder(order);
      orderDetail.setQuantity(cartDetail.getQuantity());
      orderDetail.setSubtotal(cartDetail.getProduct().getProductPrice() * cartDetail.getQuantity());
      order.getOrderDetails().add(orderDetail);
      totalOrder += orderDetail.getSubtotal();
    }

    order.setTotal(totalOrder);

    order = orderRepository.save(order);

    cartDetailRepository.deleteByCartId(userCart.getId());
    userCart.setCartDetails(new ArrayList<>());
    cartRepository.save(userCart);

    return order;
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
