package com.example.ArkaLayers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {

  @Id
  @Column(name = "payment_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "payment_type", nullable = false)
  private String paymentType;

  @Column(name = "payment_status", nullable = false)
  private String paymentStatus;

  @Column(nullable = false)
  private double total;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date updatedAt;
}
