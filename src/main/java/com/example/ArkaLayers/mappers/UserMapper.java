package com.example.ArkaLayers.mappers;

import com.example.ArkaLayers.dtos.UserDTO;
import com.example.ArkaLayers.dtos.response.UserResponseDTO;
import com.example.ArkaLayers.entities.User;

import java.util.List;

public interface UserMapper {

  User userDtoToUser(UserDTO userDTO);

  UserResponseDTO userToUserResponseDTO(User user);

  List<UserResponseDTO> listUserToListUserResponseDTO(List<User> users);
}
