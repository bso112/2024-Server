package com.example.demo.common.scheduler;

import com.example.demo.src.payment.PaymentRepository;
import com.example.demo.src.payment.PortOneService;
import com.example.demo.src.payment.PortOneTokenRepository;
import com.example.demo.src.payment.entity.Payment;
import com.example.demo.src.payment.model.PaymentAnnotation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentScheduler {

    private final PaymentRepository paymentRepository;
    private final PortOneTokenRepository tokenRepository;
    private final PortOneService portOneService;

    @Scheduled(cron = "0 0 0 * * *")
    //@Scheduled(fixedDelay = 3600000)
    public void scheduleValidatePayment() {
        List<InvalidPayment> invalidPayments = validateYesterdayPayment();
        if (!invalidPayments.isEmpty()) {
            //검증 실패처리
            log.error("유효하지 않은 결제정보: {}", invalidPayments);
        }
    }

    /**
     * 하루 전 발생한 결제 정보를 포트원 서버의 결제 정보와 비교해서 검증한다.
     *
     * @return 유효하지 않은 결제정보 리스트를 리턴한다.
     */
    private List<InvalidPayment> validateYesterdayPayment() {
        try {
            Calendar calendar = new GregorianCalendar();
            Date toDate = calendar.getTime();
            calendar.add(Calendar.DATE, -1);
            Date fromDate = calendar.getTime();

            boolean hasNextPayment = true;
            int currentPage = 0;
            final int MAX_PAYMENT_ANNOTATION_SIZE_PER_REQUEST = 100;

            ArrayList<InvalidPayment> invalidPayments = new ArrayList<>();

            //portOne에서 결제정보를 가져와서 (최대 100개씩) db에 저장된 결제정보와 비교해서 검증한다.
            while (hasNextPayment) {
                Pageable pageable = PageRequest.of(currentPage, MAX_PAYMENT_ANNOTATION_SIZE_PER_REQUEST);
                Slice<Payment> paymentSlice = paymentRepository.findAllByApprovedAtBetween(fromDate, toDate, pageable);

                hasNextPayment = paymentSlice.hasNext();

                List<Payment> payments = paymentSlice.toList();
                List<String> paymentImpIds = payments.stream().map(Payment::getImpUid).collect(Collectors.toList());
                List<PaymentAnnotation> paymentAnnotations = portOneService.getPayments(tokenRepository.getToken(), paymentImpIds);

                for (Payment payment : payments) {
                    for (PaymentAnnotation annotation : paymentAnnotations) {
                        if (payment.getAmount() != annotation.getAmount()) {
                            invalidPayments.add(new InvalidPayment(payment, annotation));
                        }
                    }
                }
            }
            return invalidPayments;
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}

