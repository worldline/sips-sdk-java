package com.worldline.sips.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.worldline.sips.SIPSRequest;
import com.worldline.sips.configuration.PaymentConfiguration;
import com.worldline.sips.model.PaymentMeanBrand;
import com.worldline.sips.model.data.Address;
import com.worldline.sips.model.data.CaptureMode;
import com.worldline.sips.model.data.Contact;
import com.worldline.sips.model.data.Currency;
import com.worldline.sips.model.data.CustomerAddress;
import com.worldline.sips.model.data.CustomerContact;
import com.worldline.sips.model.data.Language;
import com.worldline.sips.model.data.OrderChannel;
import com.worldline.sips.model.data.PaypageData;
import com.worldline.sips.model.data.S10TransactionReference;
import com.worldline.sips.model.response.InitializationResponse;
import java.net.URL;
import java.util.TreeSet;

/**
 * Request to initialize a session with the Worldline SIPS API.
 * The possible values of each field are described in the API doc.
 *
 * @see PaymentConfiguration
 */
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentRequest extends SIPSRequest<InitializationResponse> {

    private final TreeSet<PaymentMeanBrand> paymentMeanBrandList = new TreeSet<>();

    private Integer amount;
    private URL automaticResponseUrl;
    private Address billingAddress;
    private Contact billingContact;
    private Integer captureDay;
    private CaptureMode captureMode;
    private Currency currencyCode;
    private CustomerAddress customerAddress;
    private CustomerContact customerContact;
    private String customerEmail;
    private String customerId;
    private String customerIpAddress;
    private Language customerLanguage;
    private Address deliveryAddress;
    private Contact deliveryContact;
    private Address holderAddress;
    private Contact holderContact;
    private String intermediateServiceProviderId;
    private Integer keyVersion;
    private String merchantId;
    private String merchantSessionId;
    private String merchantWalletId;
    private URL normalReturnUrl;
    private OrderChannel orderChannel;
    private String orderId;
    private String returnContext;
    private String transactionOrigin;
    private String transactionReference;
    private String statementReference;
    private String templateName;
    private PaypageData paypageData;
    private S10TransactionReference s10TransactionReference;
    
    private String interfaceVersion = PaymentConfiguration.INTERFACE_VERSION;

    public PaymentRequest() {
        super("");
    }

  public String getInterfaceVersion() {
    return interfaceVersion;
  }

  public void setInterfaceVersion(String interfaceVersion) {
    this.interfaceVersion = interfaceVersion;
  }

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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerIpAddress() {
        return customerIpAddress;
    }

    public void setCustomerIpAddress(String customerIpAddress) {
        this.customerIpAddress = customerIpAddress;
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

    public String getIntermediateServiceProviderId() {
        return intermediateServiceProviderId;
    }

    public void setIntermediateServiceProviderId(String intermediateServiceProviderId) {
        this.intermediateServiceProviderId = intermediateServiceProviderId;
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

    public String getMerchantSessionId() {
        return merchantSessionId;
    }

    public void setMerchantSessionId(String merchantSessionId) {
        this.merchantSessionId = merchantSessionId;
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

    public String getReturnContext() {
        return returnContext;
    }

    public void setReturnContext(String returnContext) {
        this.returnContext = returnContext;
    }

    public String getTransactionOrigin() {
        return transactionOrigin;
    }

    public void setTransactionOrigin(String transactionOrigin) {
        this.transactionOrigin = transactionOrigin;
    }

    public TreeSet<PaymentMeanBrand> getPaymentMeanBrandList() {
        return paymentMeanBrandList;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
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

  public S10TransactionReference getS10TransactionReference() {
    return s10TransactionReference;
  }

  public void setS10TransactionReference(S10TransactionReference s10TransactionReference) {
    this.s10TransactionReference = s10TransactionReference;
  }

  @Override
    public Class<InitializationResponse> getResponseType() {
        return InitializationResponse.class;
    }
}
