package com.example.demo.src.subscription;

import com.example.demo.src.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByDueDateBetween(Date fromDate, Date toDate);
}
