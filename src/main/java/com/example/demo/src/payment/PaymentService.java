package com.example.demo.src.payment;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.payment.entity.PaymentStatus;
import com.example.demo.src.payment.model.CancelPaymentReq;
import com.example.demo.src.payment.model.CancelPaymentRes;
import com.example.demo.src.product.ProductRepository;
import com.example.demo.src.payment.entity.PaymentMethod;
import com.example.demo.src.product.entity.Product;
import com.example.demo.src.payment.model.PaymentReq;
import com.example.demo.src.order.OrderRepository;
import com.example.demo.src.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

import static com.example.demo.common.response.BaseResponseStatus.*;

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

    public void savePayment(PaymentReq paymentReq) {
        try {
            trySavePayment(paymentReq);
        } finally {
            paymentHistoryRepository.save(paymentReq.toHistory());
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

            paymentRepository.save(paymentReq.toEntity(PaymentStatus.Normal));

        } catch (Exception e) {
            //결제 취소처리
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Authorization", portOneTokenRepository.getToken());
            HttpEntity<CancelPaymentReq> entity = new HttpEntity<>(new CancelPaymentReq(paymentReq.getImpUid()), httpHeaders);

            CancelPaymentRes res = restTemplate.postForObject("https://api.iamport.kr/payments/cancel", entity, CancelPaymentRes.class);

            if (res == null || res.getCode() != 0) {
                throw new BaseException(CANCEL_FAIL_PAYMENT);
            }
            throw e;
        }
    }
}
