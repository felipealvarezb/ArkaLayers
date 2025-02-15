package com.example.ArkaLayers.service.Implementation;

import com.example.ArkaLayers.entities.User;
import com.example.ArkaLayers.repositories.UserRepository;
import com.example.ArkaLayers.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

    return existingUser;
  }

  @Override
  public User createUser(User user) {

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  @Override
  public User updateUser(Long id, User user) {
    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

    existingUser.setName(user.getName());
    existingUser.setEmail(user.getEmail());
    existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
    existingUser.setPhone(user.getPhone());
    existingUser.setActive(user.isActive());

    return userRepository.save(existingUser);
  }

  @Override
  public String deleteUserById(Long id) {
    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    userRepository.delete(existingUser);

    return "User deleted successfully";
  }
}
