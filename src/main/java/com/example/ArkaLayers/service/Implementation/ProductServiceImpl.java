package com.example.ArkaLayers.service.Implementation;

import com.example.ArkaLayers.entities.Product;
import com.example.ArkaLayers.repositories.ProductRepository;
import com.example.ArkaLayers.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public List<Product> findProductsByCategory(Long categoryId) {
    List<Product> products = productRepository.findProductByCategory(categoryId);
    return products;
  }

  @Override
  public Product getProductById(Long id) {
    Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

    return existingProduct;
  }

  @Override
  public Product createProduct(Product product) {
    Product savedProduct = productRepository.save(product);
    return savedProduct;
  }

  @Override
  public Product assignCategory(Long productId, Long categoryId) {
    return null;
  }

  @Override
  public Product unassignCategory(Long productId, Long categoryId) {
    return null;
  }

  @Override
  public Product updateProduct(Long id, Product product) {
    Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

    existingProduct.setName(product.getName());
    existingProduct.setDescription(product.getDescription());
    existingProduct.setBrand(product.getBrand());
    existingProduct.setAttributes(product.getAttributes());
    existingProduct.setProductPrice(product.getProductPrice());
    existingProduct.setActive(product.isActive());

    return productRepository.save(existingProduct);
  }

  @Override
  public String deleteProduct(Long id) {
    Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

    productRepository.delete(existingProduct);

    return "Product deleted successfully";
  }
}
