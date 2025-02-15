package com.example.ArkaLayers.repositories;

import com.example.ArkaLayers.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
