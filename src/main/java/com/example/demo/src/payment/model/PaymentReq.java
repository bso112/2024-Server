package com.example.demo.src.payment.model;

import com.example.demo.common.Constant;
import com.example.demo.src.payment.entity.Payment;
import com.example.demo.src.payment.entity.PaymentHistory;
import com.example.demo.src.payment.entity.PaymentMethod;
import com.example.demo.src.payment.entity.PaymentStatus;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReq {

    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull
    private Long orderId;
    @NotNull
    private int price;
    @NotNull
    private String impUid;
    @NotNull
    private PaymentMethod paymentMethod;

    public Payment toEntity(PaymentStatus paymentStatus) {
        return Payment.builder()
                .userId(userId)
                .productId(productId)
                .orderId(orderId)
                .amount(price)
                .paymentMethod(paymentMethod)
                .paymentStatus(paymentStatus)
                .requestAt(new Date())
                .approvedAt(new Date())
                .build();
    }

    public PaymentHistory toHistory(){
        return PaymentHistory.builder()
                .userId(userId)
                .productId(productId)
                .orderId(orderId)
                .amount(price)
                .paymentMethod(paymentMethod)
                .requestAt(new Date())
                .build();
    }
}
