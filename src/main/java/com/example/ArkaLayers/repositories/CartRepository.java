package com.example.ArkaLayers.repositories;

import com.example.ArkaLayers.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

  @Query("SELECT c FROM Cart c WHERE c.status = 'Abandoned'")
  List<Cart> getAbandonedCarts();

  @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
  Optional<Cart> findByUserId(Long userId);
}
