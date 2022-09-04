package com.sandbox.sheka.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeInformationDto
{
    private String categoryPurpose;
    private String localInstrument;
    private String serviceLevel;
}
