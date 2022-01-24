package com.worldline.sips.api.model.data;

import com.worldline.sips.util.ObjectMapperHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WalletResponseDeserializeCodeTest {

  @Test
  public void deserializeKnownCode() throws Exception {
    WalletResponseCode red = ObjectMapperHolder.INSTANCE.get().readerFor(WalletResponseCode.class).readValue("\"00\"");
    Assertions.assertEquals("00", red.getCode());
    Assertions.assertEquals(NamedWalletResponseCode.SUCCESS, red);
  }

  @Test
  public void deserializeUnknownCode() throws Exception {
    WalletResponseCode red = ObjectMapperHolder.INSTANCE.get().readerFor(WalletResponseCode.class).readValue("\"01\"");
    Assertions.assertEquals("01", red.getCode());
    Assertions.assertEquals(UnknownResponseCode.class, red.getClass());
  }
}
