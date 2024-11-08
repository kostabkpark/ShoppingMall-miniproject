package org.example.shoppingmall_miniproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @Column(length = 20)
    private String productName;
    private int cost;
    private int price;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private LocalDate registrationDate;
    private LocalDate changeStatusDate;

    // 가격 변경 불가
    // 편의 메서드
    public void changeStatus(ProductStatus status){
        this.status = status;
        this.changeStatusDate = LocalDate.now();
    }
}
