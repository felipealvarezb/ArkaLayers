package com.example.ArkaLayers.controllers;

import com.example.ArkaLayers.dtos.UserDTO;
import com.example.ArkaLayers.dtos.response.UserResponseDTO;
import com.example.ArkaLayers.entities.User;
import com.example.ArkaLayers.mappers.UserMapper;
import com.example.ArkaLayers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    List<UserResponseDTO> userResponseDTOS = userMapper.listUserToListUserResponseDTO(users);
    return ResponseEntity.ok().body(userResponseDTOS);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
    User user = userService.getUserById(userId);
    UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(user);
    return ResponseEntity.ok().body(userResponseDTO);
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> create(@Validated @RequestBody UserDTO userDTO) {
    User user = userMapper.userDtoToUser(userDTO);
    User savedUser = userService.createUser(user);
    UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(savedUser);
    return ResponseEntity.ok().body(userResponseDTO);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserResponseDTO> update(@PathVariable Long userId, @Validated @RequestBody UserDTO userDTO) {
    User user = userMapper.userDtoToUser(userDTO);
    User updatedUser = userService.updateUser(userId, user);
    UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(updatedUser);
    return ResponseEntity.ok().body(userResponseDTO);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> delete(@PathVariable Long userId) {
    String message = userService.deleteUserById(userId);
    return ResponseEntity.ok().body(message);
  }
}
