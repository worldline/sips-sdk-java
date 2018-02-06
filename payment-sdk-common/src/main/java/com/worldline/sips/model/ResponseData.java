package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.worldline.sips.helper.BooleanDeserializer;
import com.worldline.sips.helper.RuleResultListDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData {
    private int amount;
    private AcquirerResponseCode acquirerResponseCode;
    private String authorisationId;
    private int captureDay;
    private LocalDate captureLimitDate;
    private CaptureMode captureMode;
    private CardCSCResultCode cardCSCResultCode;
    private Currency currencyCode;
    private String customerIpAddress;
    private GuaranteeIndicator guaranteeIndicator;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private boolean holderAuthentRelegation;
    private HolderAuthentMethod holderAuthentMethod;
    private HolderAuthentProgram holderAuthentProgram;
    private HolderAuthentStatus holderAuthentStatus;
    private String keyVersion;
    private String maskedPan;
    private String merchantId;
    private OrderChannel orderChannel;
    private PanEntryMode panEntryMode;
    private YearMonth panExpiryDate;
    private PaymentMeanBrand paymentMeanBrand;
    private PaymentMeanType paymentMeanType;
    private PaymentPattern paymentPattern;
    @JsonDeserialize(using = RuleResultListDeserializer.class)
    private List<RuleResult> preAuthorisationRuleResultList;
    private ResponseCode responseCode;
    private ScoreColor scoreColor;
    private String statementReference;
    private String tokenPan;
    private LocalDateTime transactionDateTime;
    private String transactionOrigin;
    private String transactionReference;
    private WalletType walletType;

    public int getAmount() {
        return amount;
    }

    public AcquirerResponseCode getAcquirerResponseCode() {
        return acquirerResponseCode;
    }

    public String getAuthorisationId() {
        return authorisationId;
    }

    public int getCaptureDay() {
        return captureDay;
    }

    public LocalDate getCaptureLimitDate() {
        return captureLimitDate;
    }

    public CaptureMode getCaptureMode() {
        return captureMode;
    }

    public CardCSCResultCode getCardCSCResultCode() {
        return cardCSCResultCode;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public String getCustomerIpAddress() {
        return customerIpAddress;
    }

    public GuaranteeIndicator getGuaranteeIndicator() {
        return guaranteeIndicator;
    }

    public boolean isHolderAuthentRelegation() {
        return holderAuthentRelegation;
    }

    public HolderAuthentMethod getHolderAuthentMethod() {
        return holderAuthentMethod;
    }

    public HolderAuthentProgram getHolderAuthentProgram() {
        return holderAuthentProgram;
    }

    public HolderAuthentStatus getHolderAuthentStatus() {
        return holderAuthentStatus;
    }

    public String getKeyVersion() {
        return keyVersion;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public OrderChannel getOrderChannel() {
        return orderChannel;
    }

    public PanEntryMode getPanEntryMode() {
        return panEntryMode;
    }

    public YearMonth getPanExpiryDate() {
        return panExpiryDate;
    }

    public PaymentMeanBrand getPaymentMeanBrand() {
        return paymentMeanBrand;
    }

    public PaymentMeanType getPaymentMeanType() {
        return paymentMeanType;
    }

    public PaymentPattern getPaymentPattern() {
        return paymentPattern;
    }

    public List<RuleResult> getPreAuthorisationRuleResultList() {
        return preAuthorisationRuleResultList;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public ScoreColor getScoreColor() {
        return scoreColor;
    }

    public String getStatementReference() {
        return statementReference;
    }

    public String getTokenPan() {
        return tokenPan;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public String getTransactionOrigin() {
        return transactionOrigin;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public WalletType getWalletType() {
        return walletType;
    }
}