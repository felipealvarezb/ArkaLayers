package com.example.ArkaLayers.mappers.implementation;

import com.example.ArkaLayers.dtos.CategoryDTO;
import com.example.ArkaLayers.dtos.ProductDTO;
import com.example.ArkaLayers.dtos.response.ProductResponseDTO;
import com.example.ArkaLayers.entities.Category;
import com.example.ArkaLayers.entities.Product;
import com.example.ArkaLayers.entities.ProductCategory;
import com.example.ArkaLayers.mappers.ProductMapper;
import com.example.ArkaLayers.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapperImpl implements ProductMapper {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public Product productDTOToProduct(ProductDTO productDTO) {
    Product product = new Product();
    product.setName(productDTO.getName());
    product.setDescription(productDTO.getDescription());
    product.setBrand(productDTO.getBrand());
    product.setAttributes(productDTO.getAttributes());
    product.setProductPrice(productDTO.getProductPrice());
    product.setActive(productDTO.isActive());

    List<ProductCategory> productCategories = productDTO.getCategoryIds().stream()
            .map(categoryId -> {
              Category category = categoryRepository.findById(categoryId)
                      .orElseThrow(() -> new EntityNotFoundException("Category not found"));
              return new ProductCategory(null, product, category);
            }).collect(Collectors.toList());

    product.setProductCategories(productCategories);
    return product;
  }

  @Override
  public ProductResponseDTO productToProductResponseDTO(Product product) {
    ProductResponseDTO productResponseDTO = new ProductResponseDTO();
    productResponseDTO.setName(product.getName());
    productResponseDTO.setDescription(product.getDescription());
    productResponseDTO.setBrand(product.getBrand());
    productResponseDTO.setAttributes(product.getAttributes());
    productResponseDTO.setProductPrice(product.getProductPrice());
    productResponseDTO.setActive(product.isActive());
    productResponseDTO.setCategoryDTOList(product.getProductCategories().stream()
            .map(productCategory -> new CategoryDTO(
                    productCategory.getCategory().getName(),
                    productCategory.getCategory().getDescription()
            )).collect(Collectors.toList()));
    return productResponseDTO;
  }

  @Override
  public List<ProductResponseDTO> listProductToListProductResponseDTO(List<Product> products) {
    return products.stream()
            .map(this::productToProductResponseDTO)
            .collect(Collectors.toList());
  }
}
