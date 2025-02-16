package com.example.ArkaLayers.controllers;

import com.example.ArkaLayers.dtos.CartDTO;
import com.example.ArkaLayers.dtos.response.CartResponseDTO;
import com.example.ArkaLayers.entities.Cart;
import com.example.ArkaLayers.mappers.CartMapper;
import com.example.ArkaLayers.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @Autowired
  private CartMapper cartMapper;

  @GetMapping("/abandoned")
  public ResponseEntity<List<CartResponseDTO>> getAbandonedCarts() {
    List<Cart> abandonedCarts = cartService.getAbandonedCarts();
    List<CartResponseDTO> cartResponseDTOS = cartMapper.listCartToListCartResponseDto(abandonedCarts);
    return ResponseEntity.ok().body(cartResponseDTOS);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable Long userId) {
    Cart cart = cartService.getCartByUserId(userId);
    CartResponseDTO cartResponseDTO = cartMapper.cartToCartResponseDto(cart);
    return ResponseEntity.ok().body(cartResponseDTO);
  }

  @PostMapping("/create/{userId}")
  public ResponseEntity<CartResponseDTO> create( @PathVariable Long userId){
    Cart savedCart = cartService.createCart(userId);
    CartResponseDTO cartResponseDTO = cartMapper.cartToCartResponseDto(savedCart);
    return ResponseEntity.ok().body(cartResponseDTO);
  }

  @PostMapping("/user/{userId}/product/{productId}")
  public ResponseEntity<CartResponseDTO> insertItem(@PathVariable Long userId, @PathVariable Long productId) {
    Cart cart = cartService.insertItem(userId, productId);
    CartResponseDTO cartResponseDTO = cartMapper.cartToCartResponseDto(cart);
    return ResponseEntity.ok().body(cartResponseDTO);
  }

  @DeleteMapping("/user/{userId}")
  public ResponseEntity<String> deleteCartByUserId(@PathVariable Long userId) {
    String message = cartService.deleteCartByUserId(userId);
    return ResponseEntity.ok().body(message);
  }
}
