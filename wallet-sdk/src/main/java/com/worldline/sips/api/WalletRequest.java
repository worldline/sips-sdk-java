package com.worldline.sips.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.worldline.sips.SIPS2Request;

public abstract class WalletRequest<R extends WalletResponse> extends SIPS2Request<R> {

  private final String interfaceVersion = "WR_WS_2.39";

  public WalletRequest(String endpoint) {
    super("wallet/" + endpoint);
  }

  @JsonInclude
  public String getInterfaceVersion() {
    return interfaceVersion;
  }
}
