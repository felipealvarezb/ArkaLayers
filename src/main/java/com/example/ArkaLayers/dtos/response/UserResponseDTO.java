package com.example.ArkaLayers.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

  private String name;
  private String email;
  private String phone;
  private boolean active;
  private Date createdAt;
  private Date updatedAt;
}
