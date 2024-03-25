package com.example.demo.src.subscription.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
public class Subscription {

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long paymentId;
    private Long userId;

    private Date dueDate;
}
