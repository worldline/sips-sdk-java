package com.worldline.sips.api.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public interface WalletResponseCode {
  @JsonValue
  String getCode();

  @JsonCreator
  static WalletResponseCode fromCode(String code) {
    for (NamedWalletResponseCode responseCode : NamedWalletResponseCode.values()) {
      if (responseCode.getCode().equals(code)) {
        return responseCode;
      }
    }
    return new UnknownResponseCode(code);
  }
}
