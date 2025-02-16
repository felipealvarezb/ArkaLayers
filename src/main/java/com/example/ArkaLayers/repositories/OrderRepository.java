package com.example.ArkaLayers.repositories;

import com.example.ArkaLayers.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("SELECT o FROM Order o JOIN o.orderDetails od WHERE od.product.id = :productId")
  List<Order> findOrdersByProductId(Long productId);

  @Query("SELECT o FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt < :endDate")
  List<Order> findOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate);

  @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
  List<Order> findOrdersByUserId(Long userId);
}
