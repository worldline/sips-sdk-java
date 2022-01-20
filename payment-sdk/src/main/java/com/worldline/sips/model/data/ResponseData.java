package com.worldline.sips.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.worldline.sips.helper.BooleanDeserializer;
import com.worldline.sips.helper.RuleResultListDeserializer;

import com.worldline.sips.model.PaymentMeanBrand;
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

    private String acquirerNativeResponseCode;
    private String acquirerResponseIdentifier;
    private String acquirerResponseMessage;

    private String cardProductCode;
    private String cardProductName;

    private String customerBusinessName;
    private String customerCompanyName;
    private String customerEmail;
    private String customerId;

    private String holderContactEmail;
    private String interfaceVersion;
    private String issuerCode;
    private String issuerCountryCode;
    private String issuerEnrollementIndicator;
    private String issuerWalletInformation;

    private String merchantSessionId;
    private String merchantWalletId;
    private String orderId;

    private String returnContext;



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

    public String getAcquirerNativeResponseCode() {
        return acquirerNativeResponseCode;
    }

    public String getAcquirerResponseIdentifier() {
        return acquirerResponseIdentifier;
    }

    public String getAcquirerResponseMessage() {
        return acquirerResponseMessage;
    }

    public String getCardProductCode() {
        return cardProductCode;
    }

    public String getCardProductName() {
        return cardProductName;
    }

    public String getCustomerBusinessName() {
        return customerBusinessName;
    }

    public String getCustomerCompanyName() {
        return customerCompanyName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getHolderContactEmail() {
        return holderContactEmail;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public String getIssuerCountryCode() {
        return issuerCountryCode;
    }

    public String getIssuerEnrollementIndicator() {
        return issuerEnrollementIndicator;
    }

    public String getIssuerWalletInformation() {
        return issuerWalletInformation;
    }

    public String getMerchantSessionId() {
        return merchantSessionId;
    }

    public String getMerchantWalletId() {
        return merchantWalletId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReturnContext() {
        return returnContext;
    }
}
