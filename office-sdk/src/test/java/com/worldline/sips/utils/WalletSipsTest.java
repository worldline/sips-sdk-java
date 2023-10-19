package com.worldline.sips.utils;

import com.worldline.sips.SipsClient;
import com.worldline.sips.api.configuration.OfficeEnvironment;
import com.worldline.sips.api.model.data.NamedWalletResponseCode;
import com.worldline.sips.api.model.data.WalletPaymentMeanData;
import com.worldline.sips.api.model.request.DeletePaymentMeanRequest;
import com.worldline.sips.api.model.request.GetWalletDataRequest;
import com.worldline.sips.api.model.response.DeletePaymentMeanResponse;
import com.worldline.sips.api.model.response.GetWalletDataResponse;
import com.worldline.sips.model.NamedPaymentMeanBrand;
import com.worldline.sips.util.ObjectMapperHolder;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WalletSipsTest {

    private SipsClient sipsClient;
    
    public static final String SECRET_KEY = "yJb7SGlw6-_uy7E0aq8HG_V_rGPzZDuF7tzcvS-gzD4";


  @BeforeEach
    public void setUp() throws Exception {
        sipsClient = new SipsClient(OfficeEnvironment.TEST, "011122211100002", 1, SECRET_KEY);
    }

    @Test
    void testDeleteWalletMean() throws Exception {
      DeletePaymentMeanRequest req = new DeletePaymentMeanRequest("ATCTR59_3884977603", "2");
      System.out.println(ObjectMapperHolder.INSTANCE.get().writerFor(DeletePaymentMeanRequest.class).writeValueAsString(req));
      DeletePaymentMeanResponse response = sipsClient.send(req);
      System.out.println("Response");
      System.out.println(ObjectMapperHolder.INSTANCE.get().writerFor(DeletePaymentMeanResponse.class).writeValueAsString(response));
    }

    @Test
    void testSendWalletRequest() throws Exception {
      GetWalletDataRequest req = new GetWalletDataRequest("ATCTR59_3884977603");
      System.out.println(ObjectMapperHolder.INSTANCE.get().writerFor(GetWalletDataRequest.class).writeValueAsString(req));
      GetWalletDataResponse response = sipsClient.send(req);
//      System.out.println("Response");
      System.out.println(ObjectMapperHolder.INSTANCE.get().writerFor(GetWalletDataResponse.class).writeValueAsString(response));
//      for (WalletPaymentMeanData walletPaymentMeanData : response.getWalletPaymentMeanDataList()) {
//        YearMonth panExpiryDate = walletPaymentMeanData.getPanExpiryDate();
//        // we check the expiry date (we don't care about expired cards after 3 months)
//        if (panExpiryDate == null || panExpiryDate.isAfter(YearMonth.now().plus(3, ChronoUnit.MONTHS))) {
//          System.out.println("yeet " + panExpiryDate);
//          continue;
//        }
//        System.out.println("sus " + panExpiryDate);
//      }
  }

    @Test
    void testResponseDeserialization() throws Exception {
        GetWalletDataResponse response = ObjectMapperHolder.INSTANCE.get().readerFor(GetWalletDataResponse.class)
            .readValue("{\"seal\":\"1\"}");
        Assertions.assertEquals("1", response.getSeal());


        response = ObjectMapperHolder.INSTANCE.get().readerFor(GetWalletDataResponse.class)
            .readValue("{\"walletResponseCode\":\"00\"}");
        Assertions.assertEquals(NamedWalletResponseCode.SUCCESS, response.getWalletResponseCode());


        response = ObjectMapperHolder.INSTANCE.get().readerFor(GetWalletDataResponse.class)
            .readValue("{\n"
                + "  \"walletCreationDateTime\": \"2013-12-23T05:17:26-12:00\",\n"
                + "  \"walletLastActionDateTime\": \"2014-01-19T23:16:00-12:00\",\n"
                + "  \"walletResponseCode\": \"00\",\n"
                + "  \"walletPaymentMeanDataList\": [\n"
                + "    {\n"
                + "      \"paymentMeanId\": \"14\",\n"
                + "      \"maskedPan\": \"4977##########02\",\n"
                + "      \"paymentMeanBrand\": \"SEPA_DIRECT_DEBIT\"\n"
                + "    },\n"
                + "    {\n"
                + "      \"paymentMeanId\": \"13\",\n"
                + "      \"maskedPan\": \"4977##########55\",\n"
                + "      \"paymentMeanAlias\": \"MySDD\",\n"
                + "      \"panExpiryDate\": \"201501\",\n"
                + "      \"paymentMeanBrand\": \"CB\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"seal\": \"4579cfc4044c29550327f9cba0be400129e95cb5b2639c6e301484930b4f9f94\"\n"
                + "}");
        Assertions.assertEquals(NamedWalletResponseCode.SUCCESS, response.getWalletResponseCode());
        List<WalletPaymentMeanData> list = response.getWalletPaymentMeanDataList();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
      WalletPaymentMeanData walletPaymentMeanData = list.get(0);
      Assertions.assertNull(walletPaymentMeanData.getPaymentMeanAlias());
      Assertions.assertNull(walletPaymentMeanData.getPanExpiryDate());
      Assertions.assertEquals("14", walletPaymentMeanData.getPaymentMeanId());
      Assertions.assertEquals("4977##########02", walletPaymentMeanData.getMaskedPan());
      Assertions.assertEquals(NamedPaymentMeanBrand.SEPA_DIRECT_DEBIT, walletPaymentMeanData.getPaymentMeanBrand());
      Assertions.assertNull(response.getErrorFieldName());
    }
}
