package com.worldline.sips.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.worldline.sips.api.WalletResponse;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeletePaymentMeanResponse extends WalletResponse {
    private OffsetDateTime walletActionDateTime;

    public OffsetDateTime getWalletActionDateTime() {
        return walletActionDateTime;
    }
}
