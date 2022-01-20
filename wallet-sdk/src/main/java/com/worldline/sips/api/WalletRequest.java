package com.worldline.sips.api;

import com.worldline.sips.SIPS2Request;

public abstract class WalletRequest<R extends WalletResponse> extends SIPS2Request<R> {

  public WalletRequest(String endpoint) {
    super("wallet/" + endpoint);
  }
}
