package com.sandbox.sheka.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryDto
{
    private CreditorDto creditor;
    private CreditorAccountDto creditorAccount;


}
