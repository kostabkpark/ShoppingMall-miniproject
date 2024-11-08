package org.example.shoppingmall_miniproject.service;

import org.example.shoppingmall_miniproject.dto.product.ProductCreateDto;
import org.example.shoppingmall_miniproject.exception.NotUniqueProductNameException;
import org.example.shoppingmall_miniproject.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void 제품등록테스트() {
        // given
        ProductCreateDto productDto = new ProductCreateDto(
                "test1", 100, 120
        );
        ProductCreateDto productDto1 = new ProductCreateDto(
                "test1", 100, 120
        );
        // when
        productService.addProduct(productDto);
        assertThat(productRepository.findAll().size()).isEqualTo(1) ;
        // then
        assertThrows(NotUniqueProductNameException.class, () -> {
            productService.addProduct(productDto1);
        });

    }
}