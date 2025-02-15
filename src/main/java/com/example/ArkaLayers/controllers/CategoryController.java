package com.example.ArkaLayers.controllers;

import com.example.ArkaLayers.dtos.CategoryDTO;
import com.example.ArkaLayers.dtos.response.CategoryResponseDTO;
import com.example.ArkaLayers.entities.Category;
import com.example.ArkaLayers.mappers.CategoryMapper;
import com.example.ArkaLayers.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private CategoryMapper categoryMapper;

  @GetMapping
  public ResponseEntity<List<CategoryResponseDTO>> getAll() {
    List<Category> categories = categoryService.getAllCategories();
    List<CategoryResponseDTO> categoryResponseDTOS = categoryMapper.listCategoryToListCategoryResponseDto(categories);
    return ResponseEntity.ok().body(categoryResponseDTOS);
  }

  @PostMapping
  public ResponseEntity<CategoryResponseDTO> create(@Validated @RequestBody CategoryDTO categoryDto) {
    Category category = categoryMapper.categoryDtoToCategory(categoryDto);
    Category savedCategory = categoryService.createCategory(category);
    CategoryResponseDTO categoryResponseDTO = categoryMapper.categoryToCategoryResponse(savedCategory);
    return ResponseEntity.ok().body(categoryResponseDTO);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long categoryId, @Validated @RequestBody CategoryDTO categoryDto) {
    Category category = categoryMapper.categoryDtoToCategory(categoryDto);
    Category updatedCategory = categoryService.updateCategory(categoryId, category);
    CategoryResponseDTO categoryResponseDTO = categoryMapper.categoryToCategoryResponse(updatedCategory);
    return ResponseEntity.ok().body(categoryResponseDTO);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<String> delete(@PathVariable Long categoryId) {
    String message = categoryService.deleteCategory(categoryId);
    return ResponseEntity.ok().body(message);
  }
}
