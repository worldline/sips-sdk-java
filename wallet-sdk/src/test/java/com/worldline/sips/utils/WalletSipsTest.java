package com.worldline.sips.utils;

import com.worldline.sips.SipsClient;
import com.worldline.sips.api.WalletResponseCode;
import com.worldline.sips.api.configuration.OfficeEnvironment;
import com.worldline.sips.api.model.request.GetWalletDataRequest;
import com.worldline.sips.api.model.response.GetWalletDataResponse;
import com.worldline.sips.util.ObjectMapperHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WalletSipsTest {

  private SipsClient sipsClient;

  @BeforeEach
  public void setUp() throws Exception {
    sipsClient = new SipsClient(OfficeEnvironment.TEST, "201040040170001", 1, "rxSP61eeP_oNi5TxCD7Ngy9YcwC8MLw6OlmFGGcsY54");
  }

  @Test
  void testSendWalletRequest() throws Exception {
    GetWalletDataResponse response = sipsClient.send(new GetWalletDataRequest("1"));
    System.out.println(response);
  }

  @Test
  void testResponseDeserialization() throws Exception {
    GetWalletDataResponse response = ObjectMapperHolder.INSTANCE.get().readerFor(GetWalletDataResponse.class)
        .readValue("{\"seal\":\"1\"}");
    Assertions.assertEquals("1", response.getSeal());


    response = ObjectMapperHolder.INSTANCE.get().readerFor(GetWalletDataResponse.class)
        .readValue("{\"walletResponseCode\":\"00\"}");
    Assertions.assertEquals(WalletResponseCode.SUCCESS, response.getWalletResponseCode());

  }
}
