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
public class CustomerAnnotation {
    @JsonProperty("customer_uid")
    private String customerUid;
    @JsonProperty("pg_provider")
    private String pgProvider;
    @JsonProperty("pg_id")
    private String pgId;
    @JsonProperty("card_name")
    private String cardName;
    //빌링키가 발급된 시각 UNIX timestamp
    private int inserted;
    //빌링키가 업데이트된 시각 UNIX timestamp
    private int updated;
}
