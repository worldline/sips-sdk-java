package com.worldline.sips.security;

import com.worldline.sips.SIPSRequest;
import com.worldline.sips.SIPSResponse;
import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.helper.AlphabeticalReflectionToStringBuilder;
import com.worldline.sips.helper.SealStringStyle;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

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
     * Calculate the encrypted seal for a {@link SIPSRequest} based on a given seal string.
     *
     * @param sealString the seal string for the {@link SIPSRequest} that needs to be signed
     * @param key        the merchant's secret key
     * @return the encrypted seal for the request
     * @throws SealCalculationException when the encryption fails (e.g. algorithm missing, invalid key specified)
     * @see #getSealString(SIPSRequest)
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
     * Sort &amp; concatenate the fields of a given {@link SIPSRequest}, needed to correctly sign the request.
     *
     * @param request the request that's needs to be signed
     * @return a String, formatted as described in the API docs.
     */
    public static String getSealString(SIPSRequest<?> request) {
        AlphabeticalReflectionToStringBuilder reflectionToStringBuilder = AlphabeticalReflectionToStringBuilder.newInstance(request, new SealStringStyle());
        configure(reflectionToStringBuilder);
        return reflectionToStringBuilder.toString();
    }

  /**
     * Sort &amp; concatenate the fields of a given {@link SIPSResponse}, needed to correctly verify a response.
     *
     * @param response the response that's needs to be verified
     * @return a String, formatted as described in the API docs.
     */
    public static String getSealString(SIPSResponse response) {
        AlphabeticalReflectionToStringBuilder reflectionToStringBuilder = AlphabeticalReflectionToStringBuilder.newInstance(response, new SealStringStyle());
        configure(reflectionToStringBuilder);
        return reflectionToStringBuilder.toString();
    }

  private static void configure(AlphabeticalReflectionToStringBuilder reflectionToStringBuilder) {
      reflectionToStringBuilder.addSerializer(YearMonth.class,o -> DateTimeFormatter.ofPattern("yyyyMM").format((YearMonth) o));
      reflectionToStringBuilder.addSerializer(LocalDateTime.class,o -> DateTimeFormatter.ISO_OFFSET_DATE_TIME.format((LocalDateTime) o));
      reflectionToStringBuilder.addSerializer(LocalDate.class,o -> DateTimeFormatter.BASIC_ISO_DATE.format((LocalDate) o));
      reflectionToStringBuilder.setExcludeFieldNames("keyVersion", "endpoint", "sealAlgorithm", "seal");
      reflectionToStringBuilder.setExcludeNullValues(true);
  }

}

