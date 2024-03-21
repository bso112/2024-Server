package com.example.demo.src.payment;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.payment.model.PaymentReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
    @RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> validatePayment(@Valid @ModelAttribute PaymentReq paymentRes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("error : " + bindingResult.getFieldErrors());
            return new BaseResponse<>(BaseResponseStatus.VALIDATION_FAILED);
        }
        paymentService.savePayment(paymentRes);
        return new BaseResponse<>("결제완료!");
    }


}
