package com.insurer.premium.model;

public record PremiumQuote(
    String applicantId,
    String name,
    double bmi,
    String bmiCategory,
    double loadingPercent,
    double basePremium,
    double finalPremium,
    String status
) {}
