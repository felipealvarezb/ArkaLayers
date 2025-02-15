package com.example.ArkaLayers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

  @NotBlank(message = "Product name is required")
  @NotNull
  private String name;

  @NotBlank(message = "Product description is required")
  @NotNull
  private String description;

  @NotBlank(message = "Product brand is required")
  @NotNull
  private String brand;

  @NotBlank(message = "Product attributes is required")
  @NotNull
  private String attributes;

  @NotNull(message = "Product price is required")
  private double productPrice;

  @NotNull(message = "Product status is required")
  private boolean active;

  @NotNull(message = "Product categories is required")
  private List<Long> categoryIds;
}
