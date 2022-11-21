package com.worldline.sips.model;

public class UnknownPaymentMeanType implements PaymentMeanType {
    private final String name;
  
    public UnknownPaymentMeanType(String name) {
        this.name = name;
    }
  
    @Override
    public String getName() {
        return name;
    }
}
