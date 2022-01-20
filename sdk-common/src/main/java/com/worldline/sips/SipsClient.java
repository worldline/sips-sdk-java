package com.worldline.sips;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.worldline.sips.exception.IncorrectProxyConfException;
import com.worldline.sips.exception.IncorrectSealException;
import com.worldline.sips.exception.InvalidEnvironmentException;
import com.worldline.sips.exception.InvalidMerchantException;
import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.exception.SipsException;
import com.worldline.sips.exception.SipsRequestException;
import com.worldline.sips.model.SipsEnvironment;
import com.worldline.sips.security.SealCalculator;
import com.worldline.sips.util.ObjectMapperHolder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

/**
 * Interact with <em>SIPS APIs</em>.
 */
public class SipsClient {

  private final SipsEnvironment environment;
  private final boolean proxyEnabled;
  private final String proxyHost;
  private final Integer proxyPort;
  private String merchantId;
  private Integer keyVersion;
  private String secretKey;

  /** FIXME javadoc
   * Construct a new instance of the client for a given {@link SipsEnvironment}
   *
   * @param environment the API environment to connect to.
   * @param merchantId  the merchant's ID.
   * @param keyVersion  the version of the secret key to use.
   * @param secretKey   the merchant's secret key.
   * @throws IncorrectProxyConfException when the proxy configuration is incorrect
   * @throws InvalidEnvironmentException when an unknown environment is specified
   * @throws com.worldline.sips.exception.InvalidKeyException         when the key version is null, or a key is blank, empty or null.
   * @throws InvalidMerchantException    when the key version is null, or a key is blank, empty or null.
   */
  public SipsClient(SipsEnvironment environment, String merchantId, Integer keyVersion, String secretKey)
      throws InvalidEnvironmentException, IncorrectProxyConfException, InvalidMerchantException, InvalidKeyException {
    this(environment, merchantId, keyVersion, secretKey, false, null, null);
  }

  //FIXME javadoc
  public SipsClient(SipsEnvironment environment, String merchantId, Integer keyVersion, String secretKey,
      boolean proxyEnabled, String proxyHost, Integer proxyPort)
      throws InvalidEnvironmentException, IncorrectProxyConfException, InvalidMerchantException, InvalidKeyException {
    if (environment == null) {
      throw new InvalidEnvironmentException("Invalid environment specified!");
    }

    if (proxyEnabled) {
      if(StringUtils.isBlank(proxyHost) || proxyPort == null){
        throw new IncorrectProxyConfException("ProxyEnabled is true but proxyHost or proxyPort not filled");
      }
    }

    if (StringUtils.isBlank(merchantId)) {
      throw new InvalidMerchantException("Invalid merchant ID specified!");
    }

    if (keyVersion == null) {
      throw new InvalidKeyException("Invalid key version specified!");
    }

    if (StringUtils.isBlank(secretKey)) {
      throw new InvalidKeyException("Invalid key specified!");
    }
    this.environment = environment;
    this.proxyEnabled = proxyEnabled;
    this.proxyHost = proxyHost;
    this.proxyPort = proxyPort;
    this.merchantId = merchantId;
    this.keyVersion = keyVersion;
    this.secretKey = secretKey;
  }

  /**  FIXME javadoc
   * Initialize a session with the SIPS API for given parameters.
   * This is always the first step in a payment process.
   *
   * @param paymentRequest the parameters to use during the requested session.
   * @return The API 's response for the preformed request.
   * @throws IncorrectSealException         when the response has been tampered with.
   * @throws PaymentInitializationException when initialization fails due to processing exceptions, see inner exception for details.
   * @throws SealCalculationException       when seal calculation fails, see inner excpetion for details.
   * @see PaymentRequest
   * @see InitializationResponse
   * @see #verifySeal(InitializationResponse)
   */
  public <Response extends SIPS2Response> Response send(SIPS2Request<Response> request)
      throws SipsRequestException, SipsException, SealCalculationException, IncorrectSealException {
    String fullPath = environment.getUrl() + "/" + request.getEndpoint();
    try {
      HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
      if (this.proxyEnabled){
        HttpHost httpHost = new HttpHost(this.proxyHost, this.proxyPort);
        httpClientBuilder.setProxy(httpHost);
      }
      CloseableHttpClient httpClient = httpClientBuilder.build();
      request.setMerchantId(merchantId);
      request.setKeyVersion(keyVersion);
      request.calculateSeal(secretKey);
      StringEntity requestEntity = new StringEntity(
          ObjectMapperHolder.INSTANCE.get().writerFor(request.getRealType())
              .writeValueAsString(request),
          ContentType.APPLICATION_JSON);

      HttpPost postMethod = new HttpPost(new URI(fullPath));
      postMethod.setEntity(requestEntity);

      CloseableHttpResponse rawResponse = httpClient.execute(postMethod);
      Response response =
          ObjectMapperHolder.INSTANCE.get().readerFor(request.getResponseType())
              .readValue(EntityUtils.toString(rawResponse.getEntity()));

      response.verifySeal(secretKey);
      return response;
    } catch (JsonParseException | JsonMappingException e) {
      throw new SipsRequestException("Exception while parsing request!", e);
    } catch (IOException | ParseException e) {
      throw new SipsException("Exception while processing response from server!", e);
    } catch (URISyntaxException e) {
      throw new SipsRequestException("Invalid endpoint: '" + fullPath + "' !", e);
    }
  }

  /**  FIXME javadoc
    * Decode a payment response for further processing. After the payment is made, the API will preform a
   * POST request to the URL as defined in the {@link PaymentRequest}.
    *
    * @param parameters the content of the received request, mapped as key-value pairs.
    * @return The API 's response for the preformed payment.
    * @throws IncorrectSealException         when the response has been tampered with.
    * @see PaypageResponse
   */
  public <Response extends SIPS2Response> Response decodeResponse(Class<Response> responseClass, Map<String, String> parameters) throws IncorrectSealException {
    verifySeal(parameters.get("Data"), parameters.get("Seal"), secretKey);
    return ObjectMapperHolder.INSTANCE.get().copy()
        .convertValue(parameters, responseClass);
  }

  //FIXME javadoc
  public static <Response extends SIPS2Response> Response decodeResponse(Class<Response> responseClass,
      Map<String, String> parameters, String secretKey) throws IncorrectSealException {
    verifySeal(parameters.get("Data"), parameters.get("Seal"), secretKey);
    return ObjectMapperHolder.INSTANCE.get().copy()
        .convertValue(parameters, responseClass);
  }

  /**  FIXME javadoc
   * Verify the seal of an initialization response. To avoid tampered responses when a session is initialized,
   * the seal for the received response should always be verified before returning the object to the user.
   *
   * @param response the received response upon initialization
   * @throws IncorrectSealException   when the received seal is different from the one calculated
   * @throws SealCalculationException when seal calculation fails, see inner excpetion for details.
   */
  private void verifySeal(SIPS2Response response) throws IncorrectSealException, SealCalculationException {
    if (response.getSeal() != null) {
      String correctSeal = SealCalculator.calculate(
          SealCalculator.getSealString(response), secretKey);
      if (!StringUtils.equals(correctSeal, response.getSeal())) {
        throw new IncorrectSealException("The initialization response has been tampered with!");
      }
    }
  }

  /**
   * Verify the seal of a payment page response.To avoid tampered data for processed payments,
   * the seal for the received response should always be verified before returning the object to the user.
   *
   * @param data the received response's Data attribute
   * @param seal the received response's Seal attribute
   * @throws IncorrectSealException   when the received seal is different from the one calculated
   */
  private static void verifySeal(String data, String seal, String secretKey) throws IncorrectSealException {
    String correctSeal = DigestUtils.sha256Hex(data + secretKey);
    if (!StringUtils.equals(correctSeal, seal)) {
      throw new IncorrectSealException("The payment page response has been tampered with!");
    }
  }
}
