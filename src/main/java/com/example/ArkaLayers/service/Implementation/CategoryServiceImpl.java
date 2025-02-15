package com.example.ArkaLayers.service.Implementation;

import com.example.ArkaLayers.entities.Category;
import com.example.ArkaLayers.repositories.CategoryRepository;
import com.example.ArkaLayers.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public List<Category> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    return categories;
  }

  @Override
  public Category createCategory(Category category) {
    Category savedCategory = categoryRepository.save(category);
    return savedCategory;
  }

  @Override
  public Category updateCategory(Long id, Category category) {
    Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));

    existingCategory.setName(category.getName());
    existingCategory.setDescription(category.getDescription());

    Category updatedCategory = categoryRepository.save(existingCategory);
    return updatedCategory;
  }

  @Override
  public String deleteCategory(Long id) {
    Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));

    categoryRepository.delete(existingCategory);

    return "Category deleted successfully";
  }
}
