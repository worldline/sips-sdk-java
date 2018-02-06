package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.worldline.sips.configuration.Configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Request to initialize a session with the Worldline SIPS API.
 * The possible values of each field are described in the API doc.
 *
 * @see Configuration
 */
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentRequest {
    private static final String INTERFACE_VERSION = Configuration.INTERFACE_VERSION;

    private final List<PaymentMeanBrand> paymentMeanBrandList = new ArrayList<>();

    private Integer amount;
    private URL automaticResponseUrl;
    private Address billingAddress;
    private Contact billingContact;
    private Integer captureDay;
    private CaptureMode captureMode;
    private Currency currencyCode;
    private CustomerAddress customerAddress;
    private CustomerContact customerContact;
    private String customerId;
    private Language customerLanguage;
    private Address deliveryAddress;
    private Contact deliveryContact;
    private Address holderAddress;
    private Contact holderContact;
    private Integer keyVersion;
    private String merchantId;
    private String merchantWalletId;
    private URL normalReturnUrl;
    private OrderChannel orderChannel;
    private String orderId;
    private String transactionReference;
    private String seal;
    private String statementReference;
    private String templateName;
    private PaypageData paypageData;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public URL getAutomaticResponseUrl() {
        return automaticResponseUrl;
    }

    public void setAutomaticResponseUrl(URL automaticResponseUrl) {
        this.automaticResponseUrl = automaticResponseUrl;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Contact getBillingContact() {
        return billingContact;
    }

    public void setBillingContact(Contact billingContact) {
        this.billingContact = billingContact;
    }

    public Integer getCaptureDay() {
        return captureDay;
    }

    public void setCaptureDay(Integer captureDay) {
        this.captureDay = captureDay;
    }

    public CaptureMode getCaptureMode() {
        return captureMode;
    }

    public void setCaptureMode(CaptureMode captureMode) {
        this.captureMode = captureMode;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public CustomerContact getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(CustomerContact customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Language getCustomerLanguage() {
        return customerLanguage;
    }

    public void setCustomerLanguage(Language customerLanguage) {
        this.customerLanguage = customerLanguage;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Contact getDeliveryContact() {
        return deliveryContact;
    }

    public void setDeliveryContact(Contact deliveryContact) {
        this.deliveryContact = deliveryContact;
    }

    public Address getHolderAddress() {
        return holderAddress;
    }

    public void setHolderAddress(Address holderAddress) {
        this.holderAddress = holderAddress;
    }

    public Contact getHolderContact() {
        return holderContact;
    }

    public void setHolderContact(Contact holderContact) {
        this.holderContact = holderContact;
    }

    @JsonProperty("interfaceVersion")
    public String getInterfaceVersion() {
        return INTERFACE_VERSION;
    }

    public Integer getKeyVersion() {
        return keyVersion;
    }

    public void setKeyVersion(Integer keyVersion) {
        this.keyVersion = keyVersion;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantWalletId() {
        return merchantWalletId;
    }

    public void setMerchantWalletId(String merchantWalletId) {
        this.merchantWalletId = merchantWalletId;
    }

    public URL getNormalReturnUrl() {
        return normalReturnUrl;
    }

    public void setNormalReturnUrl(URL normalReturnUrl) {
        this.normalReturnUrl = normalReturnUrl;
    }

    public OrderChannel getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(OrderChannel orderChannel) {
        this.orderChannel = orderChannel;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<PaymentMeanBrand> getPaymentMeanBrandList() {
        return paymentMeanBrandList;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }

    public String getStatementReference() {
        return statementReference;
    }

    public void setStatementReference(String statementReference) {
        this.statementReference = statementReference;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public PaypageData getPaypageData() {
        return paypageData;
    }

    public void setPaypageData(PaypageData paypageData) {
        this.paypageData = paypageData;
    }
}
