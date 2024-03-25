package com.example.demo.src.subscription.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
public class SubscriptionHistory {

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long paymentId;
    private Long userId;

    @Builder
    public SubscriptionHistory(Long paymentId, Long userId) {
        this.paymentId = paymentId;
        this.userId = userId;
    }
}
