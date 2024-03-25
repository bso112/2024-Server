package com.example.demo.src.payment;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.payment.model.PaymentAnnotation;
import com.example.demo.src.payment.model.PaymentReq;
import com.example.demo.src.payment.model.reqest.PortOneScheduledPaymentReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.example.demo.common.response.BaseResponseStatus.SCHEDULE_FAIL_PAYMENT;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> tryPayment(@Valid @ModelAttribute PaymentReq paymentReq, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("error : " + bindingResult.getFieldErrors());
            return new BaseResponse<>(BaseResponseStatus.VALIDATION_FAILED);
        }
        if (model != null) {
            PaymentReq modelReq = (PaymentReq) model.getAttribute(Constant.KEY_PAYMENT_REQUEST);
            paymentService.savePayment(modelReq);
        } else {
            paymentService.savePayment(paymentReq);
        }

        return new BaseResponse<>("결제완료!");
    }

    @GetMapping("/portone/webhook")
    public String portOneWebhook(PortOneScheduledPaymentReq req, RedirectAttributes redirectAttributes) {
        //포트원 서버에서 결제정보 조회
        PaymentAnnotation annotation = paymentService.getPortOnePaymentData(req.getImpUid());
        try {
            //정기결제 등록시 설정한 CustomData를 PaymentReq 로 변환
            PaymentReq paymentReq = new ObjectMapper().readValue(annotation.getCustomData(), PaymentReq.class);
            redirectAttributes.addFlashAttribute(Constant.KEY_PAYMENT_REQUEST, paymentReq);
        } catch (JsonProcessingException exception) {
            log.error(SCHEDULE_FAIL_PAYMENT.getMessage(), exception);
            throw new BaseException(SCHEDULE_FAIL_PAYMENT);
        }
        return "redirect:/payment";
    }

    static class Constant {
        private static final String KEY_PAYMENT_REQUEST = "KEY_PAYMENT_REQUEST";
    }
}
