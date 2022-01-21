package com.worldline.sips.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.worldline.sips.api.WalletResponse;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeletePaymentMeanResponse extends WalletResponse {
    private LocalDateTime walletActionDateTime;

    public LocalDateTime getWalletActionDateTime() {
        return walletActionDateTime;
    }
}
