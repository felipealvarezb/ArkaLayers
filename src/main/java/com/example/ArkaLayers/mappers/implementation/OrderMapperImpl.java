package com.example.ArkaLayers.mappers.implementation;

import com.example.ArkaLayers.dtos.OrderDTO;
import com.example.ArkaLayers.dtos.ProductDTO;
import com.example.ArkaLayers.dtos.UserDTO;
import com.example.ArkaLayers.dtos.response.OrderResponseDTO;
import com.example.ArkaLayers.entities.Order;
import com.example.ArkaLayers.entities.OrderDetail;
import com.example.ArkaLayers.entities.Product;
import com.example.ArkaLayers.entities.ProductCategory;
import com.example.ArkaLayers.mappers.OrderMapper;
import com.example.ArkaLayers.repositories.ProductRepository;
import com.example.ArkaLayers.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements OrderMapper {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Order orderDtoToOrder(OrderDTO orderDTO) {
    Order order = new Order();
    order.setTotal(orderDTO.getTotal());
    order.setCurrency(orderDTO.getCurrency());
    order.setNotes(orderDTO.getNotes());
    order.setStatus(orderDTO.getStatus());
    order.setUser(userRepository.findById(orderDTO.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found")
            )
    );

    List<OrderDetail> orderDetails = orderDTO.getProductIds().stream()
            .map(productId -> {
              Product product = productRepository.findById(productId)
                      .orElseThrow(() -> new EntityNotFoundException("Product not found"));
              OrderDetail orderDetail = new OrderDetail();
              orderDetail.setProduct(product);
              orderDetail.setOrder(order);
              return orderDetail;
            }).collect(Collectors.toList());

    order.setOrderDetails(orderDetails);
    return order;
  }

  @Override
  public OrderResponseDTO orderToOrderResponseDTO(Order order) {
    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
    orderResponseDTO.setTotal(order.getTotal());
    orderResponseDTO.setCurrency(order.getCurrency());
    orderResponseDTO.setNotes(order.getNotes());
    orderResponseDTO.setStatus(order.getStatus());
    orderResponseDTO.setUserDto(new UserDTO(
            order.getUser().getName(),
            order.getUser().getEmail(),
            order.getUser().getPassword(),
            order.getUser().getPhone(),
            order.getUser().isActive(),
            order.getUser().getUserRoles().stream()
                    .map(userRole -> userRole.getRol().getId()).collect(Collectors.toList())
    ));
    orderResponseDTO.setOrderDTOList(order.getOrderDetails().stream()
            .map( orderDetail -> {
                      Product product = orderDetail.getProduct();
                      List<Long> categoryIds = product.getProductCategories().stream()
                              .map(ProductCategory::getId)
                              .collect(Collectors.toList());

                      return new ProductDTO(
                              product.getName(),
                              product.getDescription(),
                              product.getBrand(),
                              product.getAttributes(),
                              product.getProductPrice(),
                              product.isActive(),
                              categoryIds
                      );
                    }).collect(Collectors.toList()));
    orderResponseDTO.setCreatedAt(order.getCreatedAt());
    orderResponseDTO.setUpdatedAt(order.getUpdatedAt());
    return orderResponseDTO;
  }

  @Override
  public List<OrderResponseDTO> listOrderToListOrderResponseDto(List<Order> orders) {
    return orders.stream()
            .map(this::orderToOrderResponseDTO)
            .collect(Collectors.toList());
  }
}
