package org.example.shoppingmall_miniproject.repository;

import org.example.shoppingmall_miniproject.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}
