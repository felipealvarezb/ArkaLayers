package com.example.ArkaLayers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

  @NotBlank(message = "Category name is required")
  @NotNull
  private String name;

  @NotBlank(message = "Category description is required")
  @NotNull
  private String description;
}