package com.example.demo.src.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleAnnotation {

    @JsonProperty("merchant_uid")
    private String merchantUid;
    @JsonProperty("schedule_at")
    private Long scheduleAt;
    private String currency;
    private int amount;
    /**
     * 웹훅이 전달될 url
     */
    @JsonProperty("notice_url")
    private String noticeUrl;

    /**
     * 예약된 결제가 수행될 때 함께 저장할 추가정보
     */
    @JsonProperty("custom_data")
    private String customData;
}
