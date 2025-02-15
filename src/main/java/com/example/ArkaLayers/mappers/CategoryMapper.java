package com.example.ArkaLayers.mappers;

import com.example.ArkaLayers.dtos.CategoryDTO;
import com.example.ArkaLayers.dtos.response.CategoryResponseDTO;
import com.example.ArkaLayers.entities.Category;

import java.util.List;

public interface CategoryMapper {

  Category categoryDtoToCategory(CategoryDTO categoryDto);

  CategoryResponseDTO categoryToCategoryResponse(Category category);

  List<CategoryResponseDTO> listCategoryToListCategoryResponseDto(List<Category> categories);

}
