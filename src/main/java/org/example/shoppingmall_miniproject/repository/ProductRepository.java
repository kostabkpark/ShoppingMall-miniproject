package org.example.shoppingmall_miniproject.repository;

import org.example.shoppingmall_miniproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
