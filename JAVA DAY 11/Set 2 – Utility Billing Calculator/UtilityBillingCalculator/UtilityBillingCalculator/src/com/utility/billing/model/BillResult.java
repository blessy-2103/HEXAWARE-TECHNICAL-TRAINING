package com.utility.billing.model;

public record BillResult(
    int units,
    String category,
    double energyCharge,
    double fixedCharge,
    double surcharge,
    double rebate,
    double totalBill
) {}
