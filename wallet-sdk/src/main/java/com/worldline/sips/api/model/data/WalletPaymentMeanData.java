package com.worldline.sips.api.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.worldline.sips.model.PaymentMeanBrand;
import java.time.YearMonth;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletPaymentMeanData {
  String maskedPan;
  YearMonth panExpiryDate;
  String paymentMeanAlias;
  PaymentMeanBrand paymentMeanBrand;
  String paymentMeanBrandCobadgedList;
  PaymentMeanData paymentMeanData;
  String transactionActors;
}
