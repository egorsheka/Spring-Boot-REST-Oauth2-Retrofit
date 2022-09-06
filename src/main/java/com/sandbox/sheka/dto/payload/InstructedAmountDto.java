package com.sandbox.sheka.dto.payload;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructedAmountDto
{
    private BigDecimal amount;
    private String currency;
}
