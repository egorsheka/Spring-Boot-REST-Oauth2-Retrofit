package com.sandbox.sheka.dto;


import java.math.BigDecimal;
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
    private BigDecimal amount;
    private String currency;
}