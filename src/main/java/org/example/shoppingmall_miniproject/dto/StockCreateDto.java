package org.example.shoppingmall_miniproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shoppingmall_miniproject.entity.Warehouse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockCreateDto {
    private Warehouse warehouse;
    private int productId;
    private long quantity;
}
