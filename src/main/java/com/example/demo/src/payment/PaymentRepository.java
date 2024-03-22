package com.example.demo.src.payment;

import com.example.demo.src.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public Slice<Payment> findAllByApprovedAtBetween(Date fromDate, Date toDate, Pageable pageable);
}
