package com.worldline.sips;

import com.worldline.sips.exception.IncorrectSealException;
import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.security.SealCalculator;
import com.worldline.sips.security.Sealable;
import org.apache.commons.lang3.StringUtils;

/**
 * An abstract response from SIPS2 that contains the seal mechanism logic
 */
public abstract class SIPS2Response implements Sealable {
    private String seal;

    /**
     * Check that this object's data has not been tempered and is conformed to its {@link #seal}
     *
     * @param secretKey the secret key used to create this response (it is the same as the one use to create the request
     *                  that induced this response
     * @throws SealCalculationException if a seal calculation failed
     * @throws IncorrectSealException   if the response's seal is incorrect
     */
    public void verifySeal(String secretKey) throws IncorrectSealException, SealCalculationException {
        if (seal != null) {
            String correctSeal = SealCalculator.calculate(
                SealCalculator.getSealString(this), secretKey);
            if (! StringUtils.equals(correctSeal, seal)) {
                throw new IncorrectSealException("The response has been tampered with!");
            }
        }
    }

    /**
     * @return this response's seal
     * @see Sealable
     */
    public String getSeal() {
        return seal;
    }

    /**
     * Set the seal of this response
     *
     * @param seal the new seal
     * @deprecated /!\ Using this method is discouraged and could lead to security false positives;
     * do not use it if you are not sure of what you're doing
     */
    @Deprecated
    public void setSeal(String seal) {
        this.seal = seal;
    }
}
