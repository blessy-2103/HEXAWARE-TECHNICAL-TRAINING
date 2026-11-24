package com.bank.loan.model;

public record Applicant(
    String id,
    String name,
    int age,
    double monthlyIncome,
    double proposedEmi,
    int creditScore,
    String employmentType,
    int existingLoans
) {}
