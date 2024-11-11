package org.example.shoppingmall_miniproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.product.ProductCreateDto;
import org.example.shoppingmall_miniproject.dto.product.ProductInquiryDto;
import org.example.shoppingmall_miniproject.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductApiController {
    private final ProductService productService;

    @GetMapping
    public List<ProductInquiryDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{prodId}")
    public ProductInquiryDto getOneProduct(@PathVariable("prodId") int prodId) {
        return productService.getOneProduct(prodId);
    }

    @PostMapping
    public ProductInquiryDto createProduct(@RequestBody ProductCreateDto productDto) {
        return productService.addProduct(productDto);
    }
}
