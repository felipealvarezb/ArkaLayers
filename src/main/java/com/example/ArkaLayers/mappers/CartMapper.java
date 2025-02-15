package com.example.ArkaLayers.mappers;

import com.example.ArkaLayers.dtos.CartDTO;
import com.example.ArkaLayers.dtos.response.CartResponseDTO;
import com.example.ArkaLayers.entities.Cart;

import java.util.List;

public interface CartMapper {

  Cart cartDtoToCart(CartDTO cartDto);

  CartResponseDTO cartToCartResponseDto(Cart cart);

  List<CartResponseDTO> listCartToListCartResponseDto(List<Cart> carts);
}
