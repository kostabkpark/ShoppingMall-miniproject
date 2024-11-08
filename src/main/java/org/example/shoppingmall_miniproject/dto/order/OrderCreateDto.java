package org.example.shoppingmall_miniproject.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shoppingmall_miniproject.entity.OrderStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class OrderCreateDto {
    private int memberId;
    private String address;
}
