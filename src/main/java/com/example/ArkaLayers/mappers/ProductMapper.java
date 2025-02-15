package com.example.ArkaLayers.mappers;

import com.example.ArkaLayers.dtos.ProductDTO;
import com.example.ArkaLayers.dtos.response.ProductResponseDTO;
import com.example.ArkaLayers.entities.Product;

import java.util.List;

public interface ProductMapper {

  Product productDTOToProduct(ProductDTO productDTO);

  ProductResponseDTO productToProductResponseDTO(Product product);

  List<ProductResponseDTO> listProductToListProductResponseDTO(List<Product> products);
}
