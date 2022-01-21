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
import com.worldline.sips.util.ObjectMapperHolder;
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
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Map;

/**
 * Interact with <em>SIPS APIs</em>.
 */
public class SipsClient {

    private final SipsEnvironment environment;
    private final boolean proxyEnabled;
    private final String proxyHost;
    private final Integer proxyPort;
    private final String merchantId;
    private final Integer keyVersion;
    private final String secretKey;

    /**
     * Construct a new instance of the client for a given {@link SipsEnvironment}.
     *
     * @param environment the API environment to connect to.
     * @param merchantId  the merchant's ID.
     * @param keyVersion  the version of the secret key to use.
     * @param secretKey   the merchant's secret key.
     * @throws IncorrectProxyConfException when the proxy configuration is incorrect.
     * @throws InvalidEnvironmentException when an unknown environment is specified.
     * @throws InvalidKeyException         when the key version is null, or a key is blank, empty or null.
     * @throws InvalidMerchantException    when the key version is null, or a key is blank, empty or null.
     */
    public SipsClient(SipsEnvironment environment, String merchantId, Integer keyVersion, String secretKey)
        throws InvalidEnvironmentException, IncorrectProxyConfException, InvalidMerchantException, InvalidKeyException {
        this(environment, merchantId, keyVersion, secretKey, false, null, null);
    }

    /**
     * Construct a new instance of the client for a given {@link SipsEnvironment} with a defined proxy.
     *
     * @param environment  the API environment to connect to.
     * @param merchantId   the merchant's ID.
     * @param keyVersion   the version of the secret key to use.
     * @param secretKey    the merchant's secret key.
     * @param proxyEnabled true if a proxy configuration is provided.
     * @param proxyHost    the host of the proxy; if proxyEnabled is true this should net be blank, empty or null otherwise it should be null.
     * @param proxyPort    the port of the proxy; if proxyEnabled is true this should net be blank, empty or null otherwise it should be null.
     * @throws IncorrectProxyConfException when the proxy configuration is incorrect.
     * @throws InvalidEnvironmentException when an unknown environment is specified.
     * @throws InvalidKeyException         when the key version is null, or a key is blank, empty or null.
     * @throws InvalidMerchantException    when the key version is null, or a key is blank, empty or null.
     */
    public SipsClient(SipsEnvironment environment, String merchantId, Integer keyVersion, String secretKey,
                      boolean proxyEnabled, String proxyHost, Integer proxyPort)
        throws InvalidEnvironmentException, IncorrectProxyConfException, InvalidMerchantException, InvalidKeyException {
        if (environment == null) {
            throw new InvalidEnvironmentException("Invalid environment specified!");
        }

        if (proxyEnabled) {
            if (StringUtils.isBlank(proxyHost) || proxyPort == null) {
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

    /**
     * Decode a SIPS response object from a map of parameters.
     *
     * @param responseClass the type of the response to construct
     * @param parameters    the content of the received request, mapped as key-value pairs.
     * @param secretKey     the secret key used to create the request that induced this response
     * @return The constructed response.
     * @throws IncorrectSealException   - If the response has been tampered with.
     * @throws IllegalArgumentException – If conversion fails due to incompatible type; if so, root cause will contain underlying
     *                                  checked exception data binding functionality threw
     */
    public static <Response extends SIPS2Response> Response decodeResponse(Class<Response> responseClass,
                                                                           Map<String, String> parameters, String secretKey) throws IncorrectSealException, IllegalArgumentException {
        verifySeal(parameters.get("Data"), parameters.get("Seal"), secretKey);
        return ObjectMapperHolder.INSTANCE.get().copy()
            .convertValue(parameters, responseClass);
    }

    /**
     * Verify the seal of a sips response. To avoid tampered data,
     * the seal for the received response should always be verified before returning the object to the user.
     *
     * @param data the received response's Data attribute
     * @param seal the received response's Seal attribute
     * @throws IncorrectSealException when the received seal is different from the one calculated
     */
    private static void verifySeal(String data, String seal, String secretKey) throws IncorrectSealException {
        String correctSeal = DigestUtils.sha256Hex(data + secretKey);
        if (! StringUtils.equals(correctSeal, seal)) {
            throw new IncorrectSealException("The payment page response has been tampered with!");
        }
    }

    /**
     * Send a request to sips and get the response synchronously.
     * <p>
     * The seal of the request is calculated, the request is send, the response is received and its seal is checked.
     *
     * @param request the request that will be sent to SIPS
     * @throws SipsRequestException     if an error occurred while serializing or sending the request
     * @throws SipsException            if an error occurred while receiving or deserializing the response
     * @throws SealCalculationException if a seal calculation failed
     * @throws IncorrectSealException   if the response's seal is incorrect
     */
    public <Response extends SIPS2Response> Response send(SIPS2Request<Response> request)
        throws SipsRequestException, SipsException, SealCalculationException, IncorrectSealException {
        String fullPath = environment.getUrl() + "/" + request.getEndpoint();
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            if (this.proxyEnabled) {
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

    /**
     * Decode a SIPS response object from a map of parameters.
     *
     * @param responseClass the type of the response to construct
     * @param parameters    the content of the received request, mapped as key-value pairs.
     * @return The constructed response.
     * @throws IncorrectSealException   - If the response has been tampered with.
     * @throws IllegalArgumentException – If conversion fails due to incompatible type; if so, root cause will contain underlying
     *                                  checked exception data binding functionality threw
     */
    public <Response extends SIPS2Response> Response decodeResponse(Class<Response> responseClass, Map<String, String> parameters)
        throws IncorrectSealException, IllegalArgumentException {
        return decodeResponse(responseClass, parameters, secretKey);
    }

    /**
     * Verify the seal of a sips response. To avoid tampered responses,
     * the seal for the received response should always be verified before returning the object to the user.
     *
     * @param response the received response upon initialization
     * @throws IncorrectSealException   when the received seal is different from the one calculated
     * @throws SealCalculationException when seal calculation fails, see inner exception for details.
     * @see SIPS2Response#verifySeal(String) = identical
     */
    private void verifySeal(SIPS2Response response) throws IncorrectSealException, SealCalculationException {
        response.verifySeal(secretKey);
    }
}
