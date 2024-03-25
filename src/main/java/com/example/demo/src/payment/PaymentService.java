package com.example.demo.src.payment;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.order.OrderRepository;
import com.example.demo.src.payment.entity.Payment;
import com.example.demo.src.payment.entity.PaymentHistory;
import com.example.demo.src.payment.entity.PaymentStatus;
import com.example.demo.src.payment.model.PaymentAnnotation;
import com.example.demo.src.payment.model.PaymentReq;
import com.example.demo.src.payment.model.ScheduleAnnotation;
import com.example.demo.src.payment.model.reqest.CancelPaymentReq;
import com.example.demo.src.payment.model.reqest.GetPortOneBillingKeyReq;
import com.example.demo.src.payment.model.reqest.SchedulePaymentReq;
import com.example.demo.src.payment.model.response.*;
import com.example.demo.src.product.ProductRepository;
import com.example.demo.src.product.entity.Product;
import com.example.demo.src.subscription.entity.SubscriptionHistory;
import com.example.demo.src.subscription.SubscriptionHistoryRepository;
import com.example.demo.src.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import static com.example.demo.common.response.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final PortOneTokenRepository portOneTokenRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SubscriptionHistoryRepository subscriptionHistoryRepository;

    public void savePayment(PaymentReq paymentReq) {
        try {
            trySavePayment(paymentReq);
        } finally {
            PaymentHistory paymentHistory = paymentReq.toHistory();
            paymentHistoryRepository.save(paymentHistory);
            subscriptionHistoryRepository.save(new SubscriptionHistory(paymentHistory.getId(), paymentHistory.getUserId()));
        }
    }

    @Transactional
    private void trySavePayment(PaymentReq paymentReq) {
        try {
            if (!orderRepository.existsById(paymentReq.getOrderId())) {
                throw new BaseException(NOT_EXIST_ORDER);
            }
            if (!userRepository.existsById(paymentReq.getUserId())) {
                throw new BaseException(NOT_EXIST_USER);
            }

            Product product = productRepository.findById(paymentReq.getProductId()).orElseThrow(() -> new BaseException(NOT_EXIST_PRODUCT));
            if (product.getPrice() != paymentReq.getPrice()) {
                throw new BaseException(INVALID_PRICE);
            }

            Payment payment = paymentReq.toEntity(PaymentStatus.Normal);
            paymentRepository.save(payment);
            scheduleSubscriptionPayment(paymentReq, payment.getId());

        } catch (Exception e) {
            //결제 취소처리
            CancelPaymentReq cancelPaymentReq = new CancelPaymentReq(paymentReq.getImpUid());
            CancelPaymentRes res = restTemplate.postForObject("https://api.iamport.kr/payments/cancel", createPortOneRequest(cancelPaymentReq), CancelPaymentRes.class);

            if (res == null || res.getCode() != 0) {
                throw new BaseException(CANCEL_FAIL_PAYMENT);
            }
            if (res.getMessage() != null) {
                log.error(res.getMessage());
            }
            throw e;
        }
    }

    @Transactional
    private void scheduleSubscriptionPayment(PaymentReq paymentReq, Long paymentId) {
        String shopId = System.getProperty("apiConfig.portone.shop-id");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, 1);
        String noticeUrl = "https://localhost:9000/payment/portone/webhook"; //TODO
        String customData = "";

        //추후 웹훅에서 paymentReq를 받을 수 있도록 customData로 사용한다.
        try {
            customData = new ObjectMapper().writeValueAsString(paymentReq);
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage());
        }

        ScheduleAnnotation annotation = new ScheduleAnnotation(shopId, calendar.getTimeInMillis() / 1000, "KRW", paymentReq.getPrice(), noticeUrl, customData);
        SchedulePaymentReq req = new SchedulePaymentReq(getCustomerUid(paymentReq, paymentId), Collections.singletonList(annotation));
        SchedulePaymentRes res = restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/schedule", createPortOneRequest(req), SchedulePaymentRes.class);
        if (res == null || res.getCode() != 0) {
            throw new BaseException(SCHEDULE_FAIL_PAYMENT);
        }
        if (res.getMessage() != null) {
            log.error(res.getMessage());
        }
    }

    @Transactional
    private String getCustomerUid(PaymentReq paymentReq, Long paymentId) {
        GetPortOneBillingKeyReq req = new GetPortOneBillingKeyReq(paymentReq.getCardNumber(), paymentReq.getExpiry());
        GetPortOneBillingKeyRes res = restTemplate.postForObject("https://api.iamport.kr/subscribe/customers/" + paymentId, createPortOneRequest(req), GetPortOneBillingKeyRes.class);
        if (res == null || res.getCode() != 0) {
            throw new BaseException(GET_FAIL_PORTONE_BILLING_KEY);
        }
        if (res.getMessage() != null) {
            log.error(res.getMessage());
        }
        return res.getResponse().getCustomerUid();
    }

    @Transactional
    public PaymentAnnotation getPortOnePaymentData(String impUid) {
        GetPortOnePaymentRes res = restTemplate.getForObject("https://api.iamport.kr/payments/" + impUid, GetPortOnePaymentRes.class);
        if (res == null || res.getCode() != 0) {
            throw new BaseException(GET_FAIL_PORTONE_BILLING_KEY);
        }
        if (res.getMessage() != null) {
            log.error(res.getMessage());
        }
        return res.getResponse();
    }


    private <T> HttpEntity<T> createPortOneRequest(T requestObject) {
        return new HttpEntity<>(requestObject, createPortOneReqHeader());
    }

    private HttpHeaders createPortOneReqHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", portOneTokenRepository.getToken());
        return httpHeaders;
    }
}
