package com.example.ArkaLayers.dtos.response;

import com.example.ArkaLayers.dtos.PaymentDTO;
import com.example.ArkaLayers.dtos.ProductDTO;
import com.example.ArkaLayers.dtos.UserDTO;
import com.example.ArkaLayers.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
  private double total;
  private String status;
  private UserDTO userDto;
  //private List<PaymentDTO> paymentDTOList;
  private List<ProductDTO> orderDTOList;
  private Date createdAt;
  private Date updatedAt;
}
