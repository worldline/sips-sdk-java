package com.worldline.sips;

import com.worldline.sips.exception.IncorrectSealException;
import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.security.SealCalculator;
import com.worldline.sips.security.Sealable;
import org.apache.commons.lang3.StringUtils;

public class SIPS2Response implements Sealable {
  private String seal;

  public void verifySeal(String secretKey) throws IncorrectSealException, SealCalculationException {
    if (seal != null) {
      String correctSeal = SealCalculator.calculate(
          SealCalculator.getSealString(this), secretKey);
      if (! StringUtils.equals(correctSeal, seal)) {
        throw new IncorrectSealException("The initialization response has been tampered with!");
      }
    }
  }

  public String getSeal() {
    return seal;
  }

  public void setSeal(String seal) {
    this.seal = seal;
  }
}
