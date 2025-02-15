package com.example.ArkaLayers.dtos.response;

import com.example.ArkaLayers.dtos.CategoryDTO;
import com.example.ArkaLayers.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

  private String name;
  private String description;
  private String brand;
  private String attributes;
  private double productPrice;
  private boolean active;
  private List<CategoryDTO> categoryDTOList;
}
