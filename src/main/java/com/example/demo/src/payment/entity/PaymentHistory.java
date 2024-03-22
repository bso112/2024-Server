package com.example.demo.src.payment.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
public class PaymentHistory {
    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private Date requestAt;

    @Builder
    public PaymentHistory(Long id, Long orderId, Long userId, Long productId, int amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus, Date requestAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.requestAt = requestAt;
    }
}
