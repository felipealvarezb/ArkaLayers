package com.example.ArkaLayers.repositories;

import com.example.ArkaLayers.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT DISTINCT p FROM Product p JOIN p.productCategories pc WHERE pc.category.id = :categoryId")
  List<Product> findProductByCategory(Long categoryId);
}
