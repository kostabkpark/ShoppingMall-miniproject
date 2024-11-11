package org.example.shoppingmall_miniproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.product.ProductCreateDto;
import org.example.shoppingmall_miniproject.dto.product.ProductInquiryDto;
import org.example.shoppingmall_miniproject.dto.product.StockCreateDto;
import org.example.shoppingmall_miniproject.dto.product.StockInquiryDto;
import org.example.shoppingmall_miniproject.entity.Warehouse;
import org.example.shoppingmall_miniproject.service.ProductService;
import org.example.shoppingmall_miniproject.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockApiController {
    private final StockService stockService;

    @GetMapping("/{prodId}")
    public StockInquiryDto getOneStock(@PathVariable("prodId") int prodId) {
        return stockService.getOneStock(prodId);
    }

    @PostMapping
    public void createStock(@RequestBody StockCreateDto stockDto) {
        int stockId = stockService.addStock(stockDto);
        if(stockId == 0) {
            throw new RuntimeException("재고자료 생성 오류");
        };
    }
}
