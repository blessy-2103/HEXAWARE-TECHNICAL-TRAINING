package com.bank.loan.model;

public record LoanOffer(
    String status,
    String creditBand,
    String product,
    double interestRate,
    String remarks
) {}
