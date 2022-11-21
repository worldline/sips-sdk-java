package com.worldline.sips.model;

public class UnknownPaymentMeanBrand implements PaymentMeanBrand {
    private final String realName;
  
    public UnknownPaymentMeanBrand(String realName) {
        this.realName = realName;
    }
  
    @Override
    public String getRealName() {
        return realName;
    }
}
