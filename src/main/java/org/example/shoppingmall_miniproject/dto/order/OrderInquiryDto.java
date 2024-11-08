package org.example.shoppingmall_miniproject.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shoppingmall_miniproject.entity.Order;
import org.example.shoppingmall_miniproject.entity.OrderStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class OrderInquiryDto {
    private long orderId;
    private int memberId;
    private String memberName; // N+1 쿼리 테스트 해보기
    private LocalDateTime orderDate;
    private long totalQuantity;
    private long totalPrice;
    private OrderStatus status;
    private LocalDateTime statusChangeDate;

    public static OrderInquiryDto of(Order order) {
        OrderInquiryDto dto = new OrderInquiryDto();
        dto.setOrderId(order.getOrderId());
        dto.setMemberId(order.getMember().getMemberId());
        dto.setMemberName(order.getMember().getMemberName());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalQuantity(order.getTotalQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getOrderStatus());
        dto.setStatusChangeDate(order.getStatusChangeDate());
        return dto;
    }
}
