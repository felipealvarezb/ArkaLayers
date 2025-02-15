package com.example.ArkaLayers.service;

import com.example.ArkaLayers.entities.Product;

import java.util.List;

public interface ProductService {

  List<Product> findProductsByCategory(Long categoryId);

  Product getProductById(Long id);

  Product createProduct(Product product);

  Product assignCategory(Long productId, Long categoryId);

  Product unassignCategory(Long productId, Long categoryId);

  Product updateProduct(Long id, Product product);

  String deleteProduct(Long id);

}
