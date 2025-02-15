package com.example.ArkaLayers.repositories;

import com.example.ArkaLayers.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
