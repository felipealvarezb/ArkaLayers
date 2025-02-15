package com.example.ArkaLayers.mappers;

import com.example.ArkaLayers.dtos.OrderDTO;
import com.example.ArkaLayers.dtos.response.OrderResponseDTO;
import com.example.ArkaLayers.entities.Order;

import java.util.List;

public interface OrderMapper {

  Order orderDtoToOrder(OrderDTO orderDTO);

  OrderResponseDTO orderToOrderResponseDTO(Order order);

  List<OrderResponseDTO> listOrderToListOrderResponseDto(List<Order> orders);
}
