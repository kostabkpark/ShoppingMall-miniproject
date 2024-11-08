package org.example.shoppingmall_miniproject.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class OrderProductCreateDto {
    // orderProduct
    private int productId;
    private int quantity;
}
