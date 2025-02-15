package com.example.ArkaLayers.repositories;

import com.example.ArkaLayers.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}
