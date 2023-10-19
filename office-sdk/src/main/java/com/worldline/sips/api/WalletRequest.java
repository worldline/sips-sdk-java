package com.worldline.sips.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.worldline.sips.SIPSRequest;
import com.worldline.sips.api.configuration.OfficeConfiguration;

public abstract class WalletRequest<R extends WalletResponse> extends SIPSRequest<R> {
  private static final String INTERFACE_VERSION = OfficeConfiguration.INTERFACE_VERSION;

    public WalletRequest(String endpoint) {
        super("wallet/" + endpoint);
    }

    @JsonInclude
    public String getInterfaceVersion() {
        return INTERFACE_VERSION;
    }
}
