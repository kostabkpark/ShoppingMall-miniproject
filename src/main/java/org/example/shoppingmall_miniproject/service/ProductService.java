package org.example.shoppingmall_miniproject.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.product.ProductCreateDto;
import org.example.shoppingmall_miniproject.dto.product.ProductInquiryDto;
import org.example.shoppingmall_miniproject.entity.Product;
import org.example.shoppingmall_miniproject.entity.ProductStatus;
import org.example.shoppingmall_miniproject.exception.NotUniqueProductNameException;
import org.example.shoppingmall_miniproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductInquiryDto> getAllProducts() {
        List<ProductInquiryDto> productDto = productRepository.findAll().stream()
                .map(product -> ProductInquiryDto.of(product))
                .collect(Collectors.toList());
        return productDto;
    }

    public ProductInquiryDto getOneProduct(int productId) {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isPresent()) {
            return ProductInquiryDto.of(byId.get());
        }
        return null;
    }

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

    private boolean checkUniqueProductName(String productName) {
        Optional<Product> byProductName = productRepository.findByProductName(productName);
        if(byProductName.isPresent()) {
            throw new NotUniqueProductNameException("이미 존재하는 제품명입니다.");
        }
        return true;
    }
}
