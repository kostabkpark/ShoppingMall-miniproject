package org.example.shoppingmall_miniproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.shoppingmall_miniproject.exception.OrderCancelException;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;
    private int quantity;
    private int price;
    @Enumerated(EnumType.STRING)
    private OrderProductStatus status;

    public void setOrder(Order order) {
        this.order = order;
    }

    // 취소 : status 변경 / order 의 status 가 ordered or partialCancelled 인 경우에만 취소가 가능함.
    // 재고가 + 되어야 하고, order의 status 와 statusChangeDate 가 변경되어야 하고
    // order의 totalQuantity 와 totalPrice 가 변경되어야 함.
    public void cancelOrderProduct(Stock stock) {
        if(order.getOrderStatus() == OrderStatus.ordered || order.getOrderStatus() == OrderStatus.partialCancelled){
            this.status = OrderProductStatus.canceled;
            stock.increaseStock(quantity);
            //order.partialOrderCancel(this);
        } else {
            throw new OrderCancelException("주문완료이거나 부분취소 상태인 경우에만 취소가능합니다. 고객센터로 문의 바랍니다.");
        }
    }

    public static OrderProduct createOrderProduct(Product product, int quantity, int price, Stock stock) {
        OrderProduct orderProduct = new OrderProduct(
           null, null, product, quantity, price, OrderProductStatus.ordered
        );
        stock.decreaseStock(quantity);
        return orderProduct;
    }

}
