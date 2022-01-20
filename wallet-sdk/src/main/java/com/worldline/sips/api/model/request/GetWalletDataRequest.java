package com.worldline.sips.api.model.request;

import com.worldline.sips.api.WalletRequest;
import com.worldline.sips.api.model.response.GetWalletDataResponse;

public class GetWalletDataRequest extends WalletRequest<GetWalletDataResponse> {

  private final String merchantWalletId;
  private String intermediateServiceProviderId;

  public GetWalletDataRequest(String merchantWalletId) {
    super("getWalletData");
    this.merchantWalletId = merchantWalletId;
  }

  @Override
  public Class<GetWalletDataResponse> getResponseType() {
    return GetWalletDataResponse.class;
  }

  public String getIntermediateServiceProviderId() {
    return intermediateServiceProviderId;
  }

  public void setIntermediateServiceProviderId(String intermediateServiceProviderId) {
    this.intermediateServiceProviderId = intermediateServiceProviderId;
  }

  public String getMerchantWalletId() {
    return merchantWalletId;
  }
}
