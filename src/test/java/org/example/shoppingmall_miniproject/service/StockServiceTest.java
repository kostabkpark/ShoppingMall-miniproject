package org.example.shoppingmall_miniproject.service;

import org.example.shoppingmall_miniproject.dto.product.ProductCreateDto;
import org.example.shoppingmall_miniproject.dto.product.ProductInquiryDto;
import org.example.shoppingmall_miniproject.dto.product.StockCreateDto;
import org.example.shoppingmall_miniproject.entity.Warehouse;
import org.example.shoppingmall_miniproject.exception.NotUniqueStockException;
import org.example.shoppingmall_miniproject.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StockServiceTest {
    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductService productService;

    @Test
    void 상품재고등록테스트() {
        // given
        ProductCreateDto productDto = new ProductCreateDto(
                "test1", 100, 120
        );
        ProductInquiryDto newProduct = productService.addProduct(productDto);
        StockCreateDto stockDto = new StockCreateDto(
                Warehouse.KR, newProduct.getProductId(),100
        );
        stockService.addStock(stockDto);
        // when
        StockCreateDto stockDto1 = new StockCreateDto(
                Warehouse.KR, newProduct.getProductId(),200
        );
        //then
        assertThrows(NotUniqueStockException.class, () -> stockService.addStock(stockDto1));

    }

}