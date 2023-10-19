package com.worldline.sips.api.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.worldline.sips.model.PaymentMeanBrand;
import java.time.YearMonth;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletPaymentMeanData {
    private String paymentMeanData;
    private String paymentMeanId;
    private String maskedPan;
    private YearMonth panExpiryDate;
    private String paymentMeanAlias;
    private PaymentMeanBrand paymentMeanBrand;
    private String[] paymentMeanCoBadgingBrandList;
    private String[] transactionActors;

    public String getPaymentMeanId() {
        return paymentMeanId;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public YearMonth getPanExpiryDate() {
        return panExpiryDate;
    }

    public String getPaymentMeanAlias() {
        return paymentMeanAlias;
    }

    public PaymentMeanBrand getPaymentMeanBrand() {
        return paymentMeanBrand;
    }

    public String[] getPaymentMeanCoBadgingBrandList() {
        return paymentMeanCoBadgingBrandList;
    }

    public String getPaymentMeanData() {
        return paymentMeanData;
    }

    public String[] getTransactionActors() {
        return transactionActors;
    }

}
