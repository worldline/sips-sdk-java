package com.worldline.sips.api;

import com.worldline.sips.SIPSResponse;

public abstract class WalletResponse extends SIPSResponse {
    private String errorFieldName;
    private WalletResponseCode walletResponseCode;

    public String getErrorFieldName() {
        return errorFieldName;
    }

    public WalletResponseCode getWalletResponseCode() {
        return walletResponseCode;
    }
}
