package com.worldline.sips.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.worldline.sips.api.WalletResponse;
import com.worldline.sips.api.model.data.WalletPaymentMeanData;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWalletDataResponse extends WalletResponse {
    private LocalDateTime walletCreationDateTime;
    private LocalDateTime walletLastActionDateTime;
    private List<WalletPaymentMeanData> walletPaymentMeanDataList;

    public LocalDateTime getWalletCreationDateTime() {
        return walletCreationDateTime;
    }

    public LocalDateTime getWalletLastActionDateTime() {
        return walletLastActionDateTime;
    }

    public List<WalletPaymentMeanData> getWalletPaymentMeanDataList() {
        return walletPaymentMeanDataList;
    }
}
