package com.worldline.sips.api.model.data;

import com.fasterxml.jackson.annotation.JsonValue;

public class UnknownResponseCode implements WalletResponseCode {

  private final String responseCode;

  public UnknownResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  @Override
  @JsonValue
  public String getCode() {
    return responseCode;
  }

  @Override
  public String toString() {
    return responseCode;
  }
}
