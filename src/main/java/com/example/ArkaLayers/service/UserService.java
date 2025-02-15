package com.example.ArkaLayers.service;

import com.example.ArkaLayers.entities.User;

import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User getUserById(Long id);

  User createUser(User user);

  User updateUser(Long id, User user);

  String deleteUserById(Long id);

}
