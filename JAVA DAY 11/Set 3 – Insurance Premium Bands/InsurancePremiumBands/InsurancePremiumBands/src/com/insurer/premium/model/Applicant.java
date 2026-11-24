package com.insurer.premium.model;

public record Applicant(
    String id,
    String name,
    int age,
    boolean smoker,
    double weightKg,
    double heightMeters,
    int policyTermYears
) {}
