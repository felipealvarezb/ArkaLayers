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
public class UserDTO {

  @NotBlank(message = "User name is required")
  @NotNull
  private String name;

  @NotBlank(message = "User email is required")
  @NotNull
  private String email;

  @NotBlank(message = "User password is required")
  @NotNull
  private String password;

  @NotBlank(message = "User phone is required")
  @NotNull
  private String phone;

  @NotNull
  private boolean active;

  @NotNull
  private List<Long> rolesId;
}
