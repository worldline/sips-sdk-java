package com.worldline.sips.api;

import com.worldline.sips.SIPSResponse;
import com.worldline.sips.api.model.data.NamedWalletResponseCode;
import com.worldline.sips.api.model.data.WalletResponseCode;


public abstract class WalletResponse extends SIPSResponse {

    private String errorFieldName;
    private WalletResponseCode walletResponseCode;

  /**
   * Available if walletResponseCode is {@link NamedWalletResponseCode#FORMAT_ERROR} or
   * {@link NamedWalletResponseCode#INVALID_DATA}
   * @return the error
   */
    public String getErrorFieldName() {
        return errorFieldName;
    }

    public WalletResponseCode getWalletResponseCode() {
        return walletResponseCode;
    }
}
