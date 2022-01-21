package com.worldline.sips.api.model.request;

import com.worldline.sips.api.WalletRequest;
import com.worldline.sips.api.model.response.DeletePaymentMeanResponse;

public class DeletePaymentMeanRequest extends WalletRequest<DeletePaymentMeanResponse> {

  private final String merchantWalletId;
  private final String paymentMeanId;
  private String intermediateServiceProviderId;

  public DeletePaymentMeanRequest(String merchantWalletId, String paymentMeanId) {
    super("deletePaymentMean");
    this.merchantWalletId = merchantWalletId;
    this.paymentMeanId = paymentMeanId;
  }

  public String getMerchantWalletId() {
    return merchantWalletId;
  }

  public String getPaymentMeanId() {
    return paymentMeanId;
  }

  public String getIntermediateServiceProviderId() {
    return intermediateServiceProviderId;
  }

  public void setIntermediateServiceProviderId(String intermediateServiceProviderId) {
    this.intermediateServiceProviderId = intermediateServiceProviderId;
  }

  @Override
  public Class<DeletePaymentMeanResponse> getResponseType() {
    return DeletePaymentMeanResponse.class;
  }
}
