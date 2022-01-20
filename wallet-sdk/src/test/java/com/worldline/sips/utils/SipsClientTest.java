package com.worldline.sips.utils;

import com.worldline.sips.SipsClient;
import com.worldline.sips.api.configuration.OfficeEnvironment;
import com.worldline.sips.api.model.request.GetWalletDataRequest;
import com.worldline.sips.api.model.response.GetWalletDataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SipsClientTest {

  private SipsClient sipsClient;

  @BeforeEach
  public void setUp() throws Exception {
    sipsClient = new SipsClient(OfficeEnvironment.TEST, "002001000000001", 1, "002001000000001_KEY1");
  }

  @Test
  void testSendWalletRequest() throws Exception {
    GetWalletDataResponse response = sipsClient.send(new GetWalletDataRequest("1"));
    System.out.println(response);
  }
}
