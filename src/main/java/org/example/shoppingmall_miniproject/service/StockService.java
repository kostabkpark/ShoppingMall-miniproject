package org.example.shoppingmall_miniproject.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.StockCreateDto;
import org.example.shoppingmall_miniproject.entity.Product;
import org.example.shoppingmall_miniproject.entity.Stock;
import org.example.shoppingmall_miniproject.entity.Warehouse;
import org.example.shoppingmall_miniproject.exception.NotUniqueStockException;
import org.example.shoppingmall_miniproject.repository.ProductRepository;
import org.example.shoppingmall_miniproject.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public int addStock(StockCreateDto stockDto) {
        Product product = productRepository.findById(stockDto.getProductId()).get();
        if(checkUniqueStock(Warehouse.KR,product)) {
            Stock stock = new Stock(
                    0, Warehouse.KR,
                    product, stockDto.getQuantity()
            );
            Stock save = stockRepository.save(stock);
            return save.getStockId();
        }
        return 0;
    }

    boolean checkUniqueStock(Warehouse warehouse, Product product) {
        Optional<Stock> byStock = stockRepository.findByWarehouseAndProduct(warehouse, product);
        if(byStock.isPresent()) {
            throw new NotUniqueStockException("이미 재고자료가 존재합니다.");
        }
        return true;
    }

}