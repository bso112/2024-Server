package com.example.demo.src.payment.model.reqest;

import com.example.demo.src.payment.model.ScheduleAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchedulePaymentReq {
    @JsonProperty("customer_uid")
    private  String customerUId;
    private List<ScheduleAnnotation> schedules;

}

