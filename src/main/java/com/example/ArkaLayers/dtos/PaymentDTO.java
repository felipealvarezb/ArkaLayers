package com.example.ArkaLayers.dtos;

import com.example.ArkaLayers.entities.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentDTO {

  @NotBlank(message = "Payment type is required")
  @NotNull
  private String paymentType;

  @NotBlank(message = "Payment status is required")
  @NotNull
  private String paymentStatus;

  @NotNull(message = "Payment total is required")
  private double total;

  @NotNull(message = "Order id is required")
  private Long orderId;
}
