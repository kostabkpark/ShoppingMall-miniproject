package org.example.shoppingmall_miniproject.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shoppingmall_miniproject.entity.Product;
import org.example.shoppingmall_miniproject.entity.Stock;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockInquiryDto {
    private int stockId;
    private int productId;
    private long quantity;

    public static StockInquiryDto of(Stock stock) {
        StockInquiryDto dto = new StockInquiryDto(
                stock.getStockId(),
                stock.getProduct().getProductId(),
                stock.getQuantity()
        );
        return dto;
    }
}
