package com.example.ArkaLayers.dtos.response;

import com.example.ArkaLayers.dtos.OrderDTO;
import com.example.ArkaLayers.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

  private String paymentType;
  private String paymentStatus;
  private double total;
  private Long orderId;
  private Date createdAt;
  private Date updatedAt;

}
