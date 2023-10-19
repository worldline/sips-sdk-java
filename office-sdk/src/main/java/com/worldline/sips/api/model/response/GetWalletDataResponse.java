package com.worldline.sips.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.worldline.sips.api.WalletResponse;
import com.worldline.sips.api.model.data.WalletPaymentMeanData;
import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWalletDataResponse extends WalletResponse {
    private OffsetDateTime walletCreationDateTime;
    private OffsetDateTime walletLastActionDateTime;

    private List<WalletPaymentMeanData> walletPaymentMeanDataList;

    public OffsetDateTime getWalletCreationDateTime() {
        return walletCreationDateTime;
    }

    public OffsetDateTime getWalletLastActionDateTime() {
        return walletLastActionDateTime;
    }

    public List<WalletPaymentMeanData> getWalletPaymentMeanDataList() {
        return walletPaymentMeanDataList;
    }
}
