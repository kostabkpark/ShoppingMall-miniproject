package org.example.shoppingmall_miniproject.repository;

import org.example.shoppingmall_miniproject.entity.Order;
import org.example.shoppingmall_miniproject.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrder(Order order);
}
