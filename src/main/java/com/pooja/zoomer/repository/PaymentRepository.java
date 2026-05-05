package com.pooja.zoomer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pooja.zoomer.entity.Cart;
import com.pooja.zoomer.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Optional<Payment> findByOrder_OrderId(Long orderId);
}