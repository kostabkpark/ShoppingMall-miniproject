package org.example.shoppingmall_miniproject.repository;

import org.example.shoppingmall_miniproject.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
