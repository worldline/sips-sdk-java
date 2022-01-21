package com.worldline.sips.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.worldline.sips.SIPS2Request;
import com.worldline.sips.api.configuration.OfficeConfiguration;

public abstract class WalletRequest<R extends WalletResponse> extends SIPS2Request<R> {

  public WalletRequest(String endpoint) {
    super("wallet/" + endpoint);
  }

  @JsonInclude
  public String getInterfaceVersion() {
    return OfficeConfiguration.INTERFACE_VERSION;
  }
}
