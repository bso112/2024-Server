package com.example.demo.src.payment.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.payment.PaymentService;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "Payment") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Payment extends BaseEntity {
    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String impUid;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private Date requestAt;

    @Column(nullable = false)
    private Date approvedAt;

    @Builder
    public Payment(Long id, Long orderId, Long userId, Long productId, int amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus, Date requestAt, Date approvedAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = paymentStatus;
        this.requestAt = requestAt;
        this.approvedAt = approvedAt;
    }

}

