package com.example.ArkaLayers.service;

import com.example.ArkaLayers.entities.Cart;

import java.util.List;

public interface CartService {

  List<Cart> getAbandonedCarts();

  Cart createCart(Long userId);

  Cart insertItem(Long userId, Long productId);

  Cart getCartByUserId(Long userId);

  String deleteCartByUserId(Long userId);
}
