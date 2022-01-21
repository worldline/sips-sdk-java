package com.worldline.sips.security;

import com.worldline.sips.SIPS2Request;
import com.worldline.sips.SIPS2Response;
import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.helper.AlphabeticalReflectionToStringBuilder;
import com.worldline.sips.helper.SealStringStyle;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class to compute seals.
 *
 * @see Sealable
 */
public class SealCalculator {
    private SealCalculator() {
        // Nothing to see here
    }

    /**
     * Calculate the encrypted seal for a {@link SIPS2Request} based on a given seal string.
     *
     * @param sealString the seal string for the {@link SIPS2Request} that needs to be signed
     * @param key        the merchant's secret key
     * @return the encrypted seal for the request
     * @throws SealCalculationException when the encryption fails (e.g. algorithm missing, invalid key specified)
     * @see #getSealString(SIPS2Request)
     */
    public static String calculate(String sealString, String key) throws SealCalculationException {
        try {
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSHA256.init(secretKeySpec);
            return Hex.encodeHexString(hmacSHA256.doFinal(sealString.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new SealCalculationException("Seal could not be calculated!", e);
        }
    }

    /**
     * Sort &amp; concatenate the fields of a given {@link SIPS2Request}, needed to correctly sign the request.
     *
     * @param request the request that's needs to be signed
     * @return a String, formatted as described in the API docs.
     */
    public static String getSealString(SIPS2Request<?> request) {
        ReflectionToStringBuilder reflectionToStringBuilder = new AlphabeticalReflectionToStringBuilder(request, new SealStringStyle());
        reflectionToStringBuilder.setExcludeFieldNames("keyVersion", "endpoint");
        reflectionToStringBuilder.setExcludeNullValues(true);
        reflectionToStringBuilder.setAppendStatics(true);
        return reflectionToStringBuilder.toString();
    }

    /**
     * Sort &amp; concatenate the fields of a given {@link SIPS2Response}, needed to correctly verify a response.
     *
     * @param response the response that's needs to be verified
     * @return a String, formatted as described in the API docs.
     */
    public static String getSealString(SIPS2Response response) {
        ReflectionToStringBuilder reflectionToStringBuilder = new AlphabeticalReflectionToStringBuilder(response, new SealStringStyle());
        reflectionToStringBuilder.setExcludeFieldNames("seal");
        reflectionToStringBuilder.setExcludeNullValues(true);

        return reflectionToStringBuilder.toString();
    }

}

