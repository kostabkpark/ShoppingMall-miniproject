package org.example.shoppingmall_miniproject.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.ProductCreateDto;
import org.example.shoppingmall_miniproject.dto.ProductInquiryDto;
import org.example.shoppingmall_miniproject.entity.Product;
import org.example.shoppingmall_miniproject.entity.ProductStatus;
import org.example.shoppingmall_miniproject.exception.NotUniqueProductNameException;
import org.example.shoppingmall_miniproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductInquiryDto addProduct(ProductCreateDto productDto) {
        if(checkUniqueProductName(productDto.getProductName())) {
            Product product = new Product(
                    0, productDto.getProductName(),
                    productDto.getCost(), productDto.getPrice(),
                    ProductStatus.active, LocalDate.now(),LocalDate.now()
            );
            Product save = productRepository.save(product);
            return ProductInquiryDto.of(save);
        }
        return null;
    }

    boolean checkUniqueProductName(String productName) {
        Optional<Product> byProductName = productRepository.findByProductName(productName);
        if(byProductName.isPresent()) {
            throw new NotUniqueProductNameException("이미 존재하는 제품명입니다.");
        }
        return true;
    }
}
