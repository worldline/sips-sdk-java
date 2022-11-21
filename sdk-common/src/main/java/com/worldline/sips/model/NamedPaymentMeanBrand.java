package com.worldline.sips.model;

import static com.worldline.sips.model.NamedPaymentMeanType.CARD;
import static com.worldline.sips.model.NamedPaymentMeanType.CREDIT_TRANSFER;
import static com.worldline.sips.model.NamedPaymentMeanType.DIRECT_DEBIT;
import static com.worldline.sips.model.NamedPaymentMeanType.EMPTY;
import static com.worldline.sips.model.NamedPaymentMeanType.ONLINE_CREDIT;
import static com.worldline.sips.model.NamedPaymentMeanType.PROVIDER;
import static com.worldline.sips.model.NamedPaymentMeanType.VOUCHER;
import static com.worldline.sips.model.NamedPaymentMeanType.WALLET;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NamedPaymentMeanBrand implements PaymentMeanBrand {
    _1EUROCOM(ONLINE_CREDIT),
    _3XCBCOFINOGA(CARD),
    ACCEPTGIRO(CREDIT_TRANSFER),
    ACCORD(CARD),
    ACCORD_KDO(CARD),
    ACCORD_3X(ONLINE_CREDIT),
    ACCORD_4X(ONLINE_CREDIT),
    AMEX(CARD),
    AURORE(CARD),
    BCACB_3X(ONLINE_CREDIT),
    BCACB_4X(ONLINE_CREDIT),
    BCACUP(CARD),
    BCMC(CARD),
    CACF_3X(ONLINE_CREDIT),
    CACF_4X(ONLINE_CREDIT),
    CADHOC(CARD),
    CADOCARTE(CARD),
    CB(CARD),
    CBCONLINE(CREDIT_TRANSFER),
    CETELEM_3X(ONLINE_CREDIT),
    CETELEM_4X(ONLINE_CREDIT),
    COFIDIS_3X(ONLINE_CREDIT),
    COFIDIS_4X(ONLINE_CREDIT),
    CONECS(VOUCHER),
    CUP(CARD),
    CVA(CARD),
    CVCO(VOUCHER),
    DINNERS(CARD),
    ECV(VOUCHER),
    ELV(DIRECT_DEBIT),
    FIVORY(WALLET),
    FRANFINANCE_3X(ONLINE_CREDIT),
    FRANFINANCE_4X(ONLINE_CREDIT),
    GIROPAY(CREDIT_TRANSFER),
    IDEAL(CREDIT_TRANSFER),
    ILLICADO(CARD),
    INCASSO(DIRECT_DEBIT),
    JCB(CARD),
    KBCONLINE(CREDIT_TRANSFER),
    LEPOTCOMMUN(CARD),
    LYDIA(PROVIDER),
    MAESTRO(CARD),
    MASTERCARD(CARD),
    MASTERPASS(EMPTY),
    PASSCADO(CARD),
    PAY_BY_BANK(CREDIT_TRANSFER),
    PAYLIB(EMPTY),
    PAYPAL(WALLET),
    PAYTRAIL(CREDIT_TRANSFER),
    POSTFINANCE(CARD),
    PRESTO(ONLINE_CREDIT),
    SEPA_DIRECT_DEBIT(DIRECT_DEBIT),
    SOFINCO(CARD),
    SOFORTUBERWEISUNG(CREDIT_TRANSFER),
    SPIRITOFCADEAU(CARD),
    UNKNOWN(EMPTY),
    VISA(CARD),
    VISACHECKOUT(WALLET),
    VISA_ELECTRON(CARD),
    VPAY(CARD);

    final String realName;
    final NamedPaymentMeanType namedPaymentMeanType;

    NamedPaymentMeanBrand(NamedPaymentMeanType namedPaymentMeanType) {
        String name = name();
        this.namedPaymentMeanType = namedPaymentMeanType;
        this.realName = name.charAt(0) == '_' ? name.substring(1) : name;
    }

    @Override
    @JsonValue
    public String getRealName() {
        return this.realName;
    }

    @JsonIgnore
    public NamedPaymentMeanType getPaymentMeanType() {
        return namedPaymentMeanType;
    }
}
