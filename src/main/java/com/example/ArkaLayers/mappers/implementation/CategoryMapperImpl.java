package com.example.ArkaLayers.mappers.implementation;

import com.example.ArkaLayers.dtos.CategoryDTO;
import com.example.ArkaLayers.dtos.response.CategoryResponseDTO;
import com.example.ArkaLayers.entities.Category;
import com.example.ArkaLayers.mappers.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapperImpl implements CategoryMapper {
  @Override
  public Category categoryDtoToCategory(CategoryDTO categoryDto) {
    Category category = new Category();
    category.setName(categoryDto.getName());
    category.setDescription(categoryDto.getDescription());
    return category;
  }

  @Override
  public CategoryResponseDTO categoryToCategoryResponse(Category category) {
    CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
    categoryResponseDTO.setName(category.getName());
    categoryResponseDTO.setDescription(category.getDescription());
    categoryResponseDTO.setCreatedAt(category.getCreatedAt());
    categoryResponseDTO.setUpdatedAt(category.getUpdatedAt());
    return categoryResponseDTO;
  }

  @Override
  public List<CategoryResponseDTO> listCategoryToListCategoryResponseDto(List<Category> categories) {
    return categories.stream()
            .map(this::categoryToCategoryResponse)
            .collect(Collectors.toList());
  }
}
