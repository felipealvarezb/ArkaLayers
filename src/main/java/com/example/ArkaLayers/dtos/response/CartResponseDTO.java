package com.example.ArkaLayers.dtos.response;

import com.example.ArkaLayers.dtos.ProductDTO;
import com.example.ArkaLayers.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {

  private String status;
  private UserDTO userDto;
  private List<ProductDTO> productDTOList;
  private Date createdAt;
  private Date updatedAt;
}
