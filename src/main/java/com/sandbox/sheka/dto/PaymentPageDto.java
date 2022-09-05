package com.sandbox.sheka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPageDto
{

    private String iban;
    private String name;
    private String amount;
    private String currency;
}