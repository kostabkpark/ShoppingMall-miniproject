package org.example.shoppingmall_miniproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shoppingmall_miniproject.entity.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInquiryDto {
    private int productId;
    private String productName;
    private int price;

    public static ProductInquiryDto of(Product product) {
        ProductInquiryDto dto = new ProductInquiryDto(
                product.getProductId(),
                product.getProductName(),
                product.getPrice()
        );
        return dto;
    }
}
