package com.worldline.sips.api;

import com.worldline.sips.SIPS2Response;

public abstract class WalletResponse extends SIPS2Response {
  private String errorFieldName;
  private WalletResponseCode walletResponseCode;

  public String getErrorFieldName() {
    return errorFieldName;
  }

  public WalletResponseCode getWalletResponseCode() {
    return walletResponseCode;
  }
}
