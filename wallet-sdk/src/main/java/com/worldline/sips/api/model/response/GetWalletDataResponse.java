package com.worldline.sips.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.worldline.sips.api.WalletResponse;
import com.worldline.sips.api.WalletResponseCode;
import com.worldline.sips.api.model.data.WalletPaymentMeanData;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWalletDataResponse extends WalletResponse {
  private LocalDateTime walletCreationDateTime;
  private LocalDateTime walletLastActionDateTime;
  private WalletResponseCode walletResponseCode;
  private List<WalletPaymentMeanData> walletPaymentMeanDataList;
}
