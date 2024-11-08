package org.example.shoppingmall_miniproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    private LocalDateTime orderDate;
    // 총 주문수량/ 총 주문액
    private long totalQuantity;
    private long totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime statusChangeDate;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 양방향 관계, 연관관계의 주인
    @JoinColumn(name="delivery_id")
    private Delivery delivery;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    // 편의 메서드는 차후 구현
    // 주문 생성 (회원 + 주문일자 + 상태 + 재고(minus) + 배송 + 주문상품( ) : 주문총수량 + 총금액)
    public static Order createOrder(Member member, Delivery delivery, OrderProduct... orderProducts) {
        Order order = new Order(null,member,LocalDateTime.now(),0,0,
                OrderStatus.ordered,LocalDateTime.now(),delivery,new ArrayList<>());

        for (OrderProduct orderProduct : orderProducts) {
            order.totalQuantity += orderProduct.getQuantity();
            order.totalPrice += orderProduct.getPrice() * orderProduct.getQuantity();
            orderProduct.setOrder(order);
            order.orderProducts.add(orderProduct);
        }

        delivery.setOrder(order);
        return order;
    }

    // 주문 전체 취소 (주문취소일자 + 상태 + 재고(up) + 배송)
    // 취소 : status 변경 / order 의 status 가 ordered or partialCancelled 인 경우에만 취소가 가능함.
    // 재고가 + 되어야 하고, order의 status 와 statusChangeDate 가 변경되어야 하고
    // order의 totalQuantity 와 totalPrice 가 변경되어야 함.
    public void totalOrderCancel() {
        if(this.orderStatus == OrderStatus.ordered || this.orderStatus == OrderStatus.partialCancelled) {
            this.orderStatus = OrderStatus.totalCancelled;
            this.statusChangeDate = LocalDateTime.now();
            this.totalQuantity = 0;
            this.totalPrice = 0;
        }
    }
    // 주문 부분 취소 (주문취소일자 + 상태 + 재고(up) + 배송)
    // 취소 : status 변경 / order 의 status 가 ordered or partialCancelled 인 경우에만 취소가 가능함.
    // 재고가 + 되어야 하고, order의 status 와 statusChangeDate 가 변경되어야 하고
    // order의 totalQuantity 와 totalPrice 가 변경되어야 함.
    public void partialOrderCancel(OrderProduct orderProduct){
        if(this.orderStatus == OrderStatus.ordered || this.orderStatus == OrderStatus.partialCancelled) {
            this.orderStatus = OrderStatus.partialCancelled;
            this.statusChangeDate = LocalDateTime.now();
            this.totalQuantity -= orderProduct.getQuantity();
            this.totalPrice -= orderProduct.getPrice() * orderProduct.getQuantity();
        }
    }
}
