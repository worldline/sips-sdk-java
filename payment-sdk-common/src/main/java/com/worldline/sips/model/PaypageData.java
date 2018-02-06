package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaypageData {
    private boolean bypassReceiptPage;

    public boolean isBypassReceiptPage() {
        return bypassReceiptPage;
    }

    public void setBypassReceiptPage(boolean bypassReceiptPage) {
        this.bypassReceiptPage = bypassReceiptPage;
    }
}
