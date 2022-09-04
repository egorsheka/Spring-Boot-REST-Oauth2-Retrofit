package com.sandbox.sheka.dto.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayloadDto
{
    private BeneficiaryDto beneficiary;
    private String creationDateTime;
    private CreditTransferTransactionDto creditTransferTransaction;
    private int numberOfTransactions;
    private String paymentInformationId;
    private PaymentTypeInformationDto paymentTypeInformation;


}
