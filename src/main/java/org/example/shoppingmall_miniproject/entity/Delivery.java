package org.example.shoppingmall_miniproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery") // 양방향관계
    private Order order;
    @Column(length = 100)
    private String address;
    private DeliveryStatus status;
    private LocalDateTime deliveryTime;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // 편의 메서드
    public void changeStatus(DeliveryStatus status) {
        this.status = status;
        this.deliveryTime = LocalDateTime.now();
    }

}
