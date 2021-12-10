package com.worldline.sips.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.worldline.sips.api.configuration.Environment;
import com.worldline.sips.api.exception.*;
import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.model.InitializationResponse;
import com.worldline.sips.model.PaymentRequest;
import com.worldline.sips.model.PaypageResponse;
import com.worldline.sips.util.ObjectMapperHolder;
import com.worldline.sips.util.SealCalculator;
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

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * Interact with the <em>SIPS API</em> in payment page mode.
 */
public class PaypageClient {

    private final Environment environment;
    private final Integer keyVersion;
    private final String merchantId;
    private final String secretKey;
    private final String proxyHost;
    private final Integer proxyPort;
    private final boolean proxyEnabled;

    /**
     * Construct a new instance of the client for a given {@link Environment}
     *
     * @param environment the API environment to connect to.
     * @param merchantId  the merchant's ID.
     * @param keyVersion  the version of the secret key to use.
     * @param secretKey   the merchant's secret key.
     * @throws IncorrectProxyConfException when the proxy configuration is incorrect
     * @throws InvalidEnvironmentException when an unknown environment is specified
     * @throws InvalidKeyException         when the key version is null, or a key is blank, empty or null.
     * @throws InvalidMerchantException    when the key version is null, or a key is blank, empty or null.
     */
    public PaypageClient(Environment environment, String merchantId, Integer keyVersion, String secretKey)
        throws InvalidEnvironmentException, IncorrectProxyConfException, InvalidKeyException, InvalidMerchantException {
        this(environment, merchantId, keyVersion, secretKey, false, null, null);
    }

    public PaypageClient(Environment environment, String merchantId, Integer keyVersion, String secretKey, boolean proxyEnabled, String proxyHost, Integer proxyPort)
        throws InvalidEnvironmentException, InvalidMerchantException, InvalidKeyException, IncorrectProxyConfException {
        if (environment == null) {
            throw new InvalidEnvironmentException("Invalid environment specified!");
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

        if (proxyEnabled) {
            if(StringUtils.isBlank(proxyHost) || proxyPort == null){
                throw new IncorrectProxyConfException("ProxyEnabled is true but proxyHost or proxyPort not filled");
            }
        }

        this.environment = environment;
        this.keyVersion = keyVersion;
        this.merchantId = merchantId;
        this.secretKey = secretKey;
        this.proxyEnabled=proxyEnabled;
        this.proxyHost=proxyHost;
        this.proxyPort=proxyPort;
    }

    /**
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
    public InitializationResponse initialize(PaymentRequest paymentRequest) throws IncorrectSealException, PaymentInitializationException, SealCalculationException {
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            if(this.proxyEnabled){
                HttpHost httpHost = new HttpHost(this.proxyHost, this.proxyPort);
                httpClientBuilder.setProxy(httpHost);
            }
            CloseableHttpClient httpClient = httpClientBuilder.build();
            paymentRequest.setMerchantId(merchantId);
            paymentRequest.setKeyVersion(keyVersion);
            paymentRequest.setSeal(SealCalculator.calculate(
                    SealCalculator.getSealString(paymentRequest), secretKey));
            StringEntity requestEntity = new StringEntity(
                    ObjectMapperHolder.INSTANCE.get().writerFor(PaymentRequest.class)
                            .writeValueAsString(paymentRequest),
                    ContentType.APPLICATION_JSON);

            HttpPost postMethod = new HttpPost(getEnvironmentUrl());
            postMethod.setEntity(requestEntity);

            CloseableHttpResponse rawResponse = httpClient.execute(postMethod);
            InitializationResponse initializationResponse =
                    ObjectMapperHolder.INSTANCE.get().readerFor(InitializationResponse.class)
                            .readValue(EntityUtils.toString(rawResponse.getEntity()));

            verifySeal(initializationResponse);

            return initializationResponse;

        } catch (JsonParseException | JsonMappingException e) {
            throw new PaymentInitializationException("Exception while parsing PaymentRequest!", e);
        } catch (IOException | ParseException e) {
            throw new PaymentInitializationException("Exception while processing response from server!", e);
        }

    }

    /**
     * Decode a payment response for further processing. After the payment is made, the API will preform a
     * POST request to the URL as defined in the {@link PaymentRequest}.
     *
     * @param parameters the content of the received request, mapped as key-value pairs.
     * @return The API 's response for the preformed payment.
     * @throws IncorrectSealException         when the response has been tampered with.
     * @see PaypageResponse
     */
    public PaypageResponse decodeResponse(Map<String, String> parameters) throws IncorrectSealException {
        verifySeal(parameters.get("Data"), parameters.get("Seal"));
        return ObjectMapperHolder.INSTANCE.get().copy()
                .convertValue(parameters, PaypageResponse.class);
    }

    private URI getEnvironmentUrl() {
        return URI.create(environment.getUrl());
    }

    /**
     * Verify the seal of a initialization response. To avoid tampered responses when a session is initialized,
     * the seal for the received response should always be verified before returning the object to the user.
     *
     * @param initializationResponse the received response upon initialization
     * @throws IncorrectSealException   when the received seal is different from the one calculated
     * @throws SealCalculationException when seal calculation fails, see inner excpetion for details.
     */
    private void verifySeal(InitializationResponse initializationResponse) throws IncorrectSealException, SealCalculationException {
        if (initializationResponse.getSeal() != null) {
            String correctSeal = SealCalculator.calculate(
                    SealCalculator.getSealString(initializationResponse), secretKey);
            if (!StringUtils.equals(correctSeal, initializationResponse.getSeal())) {
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
    private void verifySeal(String data, String seal) throws IncorrectSealException {
        String correctSeal = DigestUtils.sha256Hex(data + secretKey);
        if (!StringUtils.equals(correctSeal, seal)) {
            throw new IncorrectSealException("The payment page response has been tampered with!");
        }
    }

}
