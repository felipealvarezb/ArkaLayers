package com.example.ArkaLayers.dtos;

import com.example.ArkaLayers.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

  @NotNull(message = "Order total is required")
  private double total;

  @NotBlank(message = "Order currency is required")
  @NotNull
  private String currency;

  @NotBlank(message = "Order notes is required")
  @NotNull
  private String notes;

  @NotBlank(message = "Order status is required")
  @NotNull
  private String status;

  @NotNull(message = "User id is required")
  private Long userId;

  @NotNull(message = "At least one product are required")
  private List<Long> productIds;
}
