package com.example.ArkaLayers.controllers;

import com.example.ArkaLayers.dtos.ProductDTO;
import com.example.ArkaLayers.dtos.response.ProductResponseDTO;
import com.example.ArkaLayers.entities.Product;
import com.example.ArkaLayers.mappers.ProductMapper;
import com.example.ArkaLayers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductMapper productMapper;

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long categoryId) {
    List<Product> products = productService.findProductsByCategory(categoryId);
    List<ProductResponseDTO> productResponseDTOS = productMapper.listProductToListProductResponseDTO(products);
    return ResponseEntity.ok().body(productResponseDTOS);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long productId) {
    Product product = productService.getProductById(productId);
    ProductResponseDTO productResponseDTO = productMapper.productToProductResponseDTO(product);
    return ResponseEntity.ok().body(productResponseDTO);
  }

  @PostMapping
  public ResponseEntity<ProductResponseDTO> create(@Validated @RequestBody ProductDTO productDto) {
    Product product = productMapper.productDTOToProduct(productDto);
    Product savedProduct = productService.createProduct(product);
    ProductResponseDTO productResponseDTO = productMapper.productToProductResponseDTO(savedProduct);
    return ResponseEntity.ok().body(productResponseDTO);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductResponseDTO> update(@PathVariable Long productId, @Validated @RequestBody ProductDTO productDto) {
    Product product = productMapper.productDTOToProduct(productDto);
    Product updatedProduct = productService.updateProduct(productId, product);
    ProductResponseDTO productResponseDTO = productMapper.productToProductResponseDTO(updatedProduct);
    return ResponseEntity.ok().body(productResponseDTO);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<String> delete(@PathVariable Long productId) {
    String message = productService.deleteProduct(productId);
    return ResponseEntity.ok().body(message);
  }
}
