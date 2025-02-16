package com.example.ArkaLayers.service.Implementation;

import com.example.ArkaLayers.entities.Cart;
import com.example.ArkaLayers.entities.CartDetail;
import com.example.ArkaLayers.entities.Product;
import com.example.ArkaLayers.entities.User;
import com.example.ArkaLayers.repositories.CartRepository;
import com.example.ArkaLayers.repositories.ProductRepository;
import com.example.ArkaLayers.repositories.UserRepository;
import com.example.ArkaLayers.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  @Override
  public List<Cart> getAbandonedCarts() {
    List<Cart> carts = cartRepository.getAbandonedCarts();
    return carts;
  }

  @Override
  public Cart createCart(Long userId) {
    User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Cart cart = new Cart();
    cart.setStatus("Pending");
    cart.setUser(existingUser);
    cart.setCartDetails(new ArrayList<>());

    return cartRepository.save(cart);
  }

  @Override
  public Cart insertItem(Long userId, Long productId) {
    Cart userCart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("User cart not found"));

    Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

    Optional<CartDetail> existingCartDetail = userCart.getCartDetails().stream()
            .filter(cd -> cd.getProduct().getId().equals(productId))
            .findFirst();

    if (existingCartDetail.isPresent()) {
      existingCartDetail.get().setQuantity(existingCartDetail.get().getQuantity() + 1);
    } else {
      CartDetail cartDetail = new CartDetail();
      cartDetail.setCart(userCart);
      cartDetail.setProduct(existingProduct);
      cartDetail.setQuantity(1);
      userCart.getCartDetails().add(cartDetail);
    }

    return cartRepository.save(userCart);
  }

  @Override
  public Cart getCartByUserId(Long userId) {
    Cart userCart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("User cart not found"));
    return userCart;
  }

  @Override
  public String deleteCartByUserId(Long userId) {
    return "";
  }
}
