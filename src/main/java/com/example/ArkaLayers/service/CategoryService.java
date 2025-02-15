package com.example.ArkaLayers.service;

import com.example.ArkaLayers.entities.Category;

import java.util.List;

public interface CategoryService {

  List<Category> getAllCategories();

  Category createCategory(Category category);

  Category updateCategory(Long id, Category category);

  String deleteCategory(Long id);
}
