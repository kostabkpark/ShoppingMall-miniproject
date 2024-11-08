package org.example.shoppingmall_miniproject.repository;

import org.example.shoppingmall_miniproject.entity.Product;
import org.example.shoppingmall_miniproject.entity.Stock;
import org.example.shoppingmall_miniproject.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByWarehouseAndProduct(Warehouse warehouse, Product product);
}
