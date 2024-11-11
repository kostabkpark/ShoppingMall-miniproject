package org.example.shoppingmall_miniproject.repository;

import org.example.shoppingmall_miniproject.entity.Member;
import org.example.shoppingmall_miniproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order>  findByMember(Member member); // 불필요함.
    List<Order> findAllByMember(Member member);
}
