package com.example.ArkaLayers.dtos;

import com.example.ArkaLayers.entities.CartDetail;
import com.example.ArkaLayers.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

  private String status;
  private Long userId;

}
