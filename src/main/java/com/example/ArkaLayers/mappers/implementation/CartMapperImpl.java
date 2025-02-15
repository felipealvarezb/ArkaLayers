package com.example.ArkaLayers.mappers.implementation;

import com.example.ArkaLayers.dtos.CartDTO;
import com.example.ArkaLayers.dtos.ProductDTO;
import com.example.ArkaLayers.dtos.UserDTO;
import com.example.ArkaLayers.dtos.response.CartResponseDTO;
import com.example.ArkaLayers.entities.Cart;
import com.example.ArkaLayers.entities.Product;
import com.example.ArkaLayers.entities.ProductCategory;
import com.example.ArkaLayers.mappers.CartMapper;
import com.example.ArkaLayers.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapperImpl implements CartMapper {

  @Autowired
  private UserRepository userRepository;
  @Override
  public Cart cartDtoToCart(CartDTO cartDto) {
    Cart cart = new Cart();
    cart.setStatus(cartDto.getStatus());
    cart.setUser(userRepository.findById(cartDto.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found")));
    cart.setCartDetails(null);
    return cart;
  }

  @Override
  public CartResponseDTO cartToCartResponseDto(Cart cart) {
    CartResponseDTO cartResponseDTO = new CartResponseDTO();
    cartResponseDTO.setStatus(cart.getStatus());
    cartResponseDTO.setUserDto(new UserDTO(
            cart.getUser().getName(),
            cart.getUser().getEmail(),
            cart.getUser().getPassword(),
            cart.getUser().getPhone(),
            cart.getUser().isActive(),
            cart.getUser().getUserRoles().stream()
                    .map(userRole -> userRole.getRol().getId()).collect(Collectors.toList())
    ));
    cartResponseDTO.setProductDTOList(cart.getCartDetails().stream()
            .map(cartDetail -> {
              Product product = cartDetail.getProduct();
              List<Long> categoryIds = product.getProductCategories().stream()
                      .map(ProductCategory::getId)
                      .collect(Collectors.toList());

              return new ProductDTO(
                      product.getName(),
                      product.getDescription(),
                      product.getBrand(),
                      product.getAttributes(),
                      product.getProductPrice(),
                      product.isActive(),
                      categoryIds
              );
            })
            .collect(Collectors.toList()));
    cartResponseDTO.setCreatedAt(cart.getCreatedAt());
    cartResponseDTO.setUpdatedAt(cart.getUpdatedAt());
    return cartResponseDTO;
  }

  @Override
  public List<CartResponseDTO> listCartToListCartResponseDto(List<Cart> carts) {
    return carts.stream()
            .map(this::cartToCartResponseDto)
            .collect(Collectors.toList());
  }
}
