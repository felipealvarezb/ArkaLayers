package com.example.ArkaLayers.service.Implementation;

import com.example.ArkaLayers.entities.Cart;
import com.example.ArkaLayers.repositories.CartRepository;
import com.example.ArkaLayers.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private CartRepository cartRepository;

  @Override
  public List<Cart> getAbandonedCarts() {
    List<Cart> carts = cartRepository.getAbandonedCarts();
    return carts;
  }

  @Override
  public Cart createCart(Long userId) {
    return null;
  }

  @Override
  public Cart getCartByUserId(Long userId) {
    return null;
  }

  @Override
  public String deleteCartByUserId(Long userId) {
    return "";
  }
}
