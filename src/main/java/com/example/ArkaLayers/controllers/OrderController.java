package com.example.ArkaLayers.controllers;

import com.example.ArkaLayers.dtos.OrderDTO;
import com.example.ArkaLayers.dtos.response.OrderResponseDTO;
import com.example.ArkaLayers.entities.Order;
import com.example.ArkaLayers.mappers.OrderMapper;
import com.example.ArkaLayers.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderMapper orderMapper;

  @GetMapping("/product/{productId}")
  public ResponseEntity<List<OrderResponseDTO>> getOrderByProductId(@PathVariable Long productId) {
    List<Order> orders = orderService.findOrdersByProductId(productId);
    List<OrderResponseDTO> orderResponseDTOS = orderMapper.listOrderToListOrderResponseDto(orders);
    return ResponseEntity.ok().body(orderResponseDTOS);
  }

  @GetMapping("/date")
  public ResponseEntity<List<OrderResponseDTO>> getOrderByDateRange(
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    LocalDateTime startDateTime = startDate.atStartOfDay();
    LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

    List<Order> orders = orderService.findOrdersByDateRange(startDateTime, endDateTime);
    List<OrderResponseDTO> orderResponseDTOS = orderMapper.listOrderToListOrderResponseDto(orders);
    return ResponseEntity.ok().body(orderResponseDTOS);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<OrderResponseDTO>> getOrderByUserId(@PathVariable Long userId) {
    List<Order> orders = orderService.findOrdersByUserId(userId);
    List<OrderResponseDTO> orderResponseDTOS = orderMapper.listOrderToListOrderResponseDto(orders);
    return ResponseEntity.ok().body(orderResponseDTOS);
  }

  @PostMapping("/create/{userId}")
  public ResponseEntity<OrderResponseDTO> create(@PathVariable Long userId) {
    Order savedOrder = orderService.createOrder(userId);
    OrderResponseDTO orderResponseDTO = orderMapper.orderToOrderResponseDTO(savedOrder);
    return ResponseEntity.ok().body(orderResponseDTO);
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<OrderResponseDTO> update(@PathVariable Long orderId, @Validated @RequestBody OrderDTO orderDto) {
    Order order = orderMapper.orderDtoToOrder(orderDto);
    Order updatedOrder = orderService.updateOrder(orderId, order);
    OrderResponseDTO orderResponseDTO = orderMapper.orderToOrderResponseDTO(updatedOrder);
    return ResponseEntity.ok().body(orderResponseDTO);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<String> delete(@PathVariable Long orderId) {
    String message = orderService.deleteOrder(orderId);
    return ResponseEntity.ok().body(message);
  }


}
