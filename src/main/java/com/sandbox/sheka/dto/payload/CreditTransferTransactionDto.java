package com.sandbox.sheka.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditTransferTransactionDto
{
    private InstructedAmountDto instructedAmount;
    private RemittanceInformationDto remittanceInformation;
    private String requestedExecutionDate;
}
