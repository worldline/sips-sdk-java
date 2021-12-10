# SIPS Payment SDK [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.worldline.sips/payment-sdk/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.worldline.sips/payment-sdk)
This package provides a JAVA implementation for SIPS, a Worldline e-payments gateway.

> :warning: This library was written for SIPS 2.0 and is not compatible with SIPS 1.0

## Before you begin
This package contains a basic connector for SIPS, based on the **SIPS Paypage JSON API**.
Please refer to the [documentation](https://documentation.sips.worldline.com) for a better understanding.

If you find field or functionality is missing, feel free to submit a PR or create an issue.  

## Installing

### using Gradle
```groovy

dependencies {
    implementation 'com.worldline.sips:payment-sdk:1.4.0'
}

```

### using Maven
```xml
<dependency>
    <groupId>com.worldline.sips</groupId>
    <artifactId>payment-sdk</artifactId>
    <version>1.4.0</version>
</dependency>
```

## Usage
> :bulb: Currently this library only supports SIPS in pay page mode.

### Initialization
First, create a client for the desired environment using your merchant ID, key version & secret key:
```java
PaypageClient paypageClient = new PaypageClient(
        Environment.TEST, 
        "002001000000002", 
        1, // This shouldn't be hardcoded here...
        "002001000000002_KEY1"); // ...and neither should this.
```
> :bulb: Merchant data for SIPS TEST can be found in the documentation.

Then set up a request to initialize a session on the SIPS server:

```java
PaymentRequest paymentRequest = new PaymentRequest();
paymentRequest.setAmount(2);
paymentRequest.setCurrencyCode(Currency.EUR);
paymentRequest.setOrderChannel(OrderChannel.INTERNET);
```
Add unique reference for the transaction:

```java
paymentRequest.setTransactionReference("My awesome transaction reference");
```

And initialize your session on the server:
```java
InitalizationResponse initializationResponse = paypageClient.initialize(paymentRequest);
```

The `initializationResponse` you'll receive from the server contains all information needed to continue 
handling your transaction. If your initialization was successful, your response will contain a 
`RedirectionStatusCode.TRANSACTION_INITIALIZED`.

### Making the payment
In case your initialization was successful, you have to use the `redirectionUrl` received to perform a POST request
with both the `redirectionData` and `seal` as parameters. Since this should redirect the customer the SIPS 
payment page, the cleanest example is a simple HTML form:

```html
<form method="post" action="redirectionUrl">
    <input name="redirectionData" type="hidden" value="..." />
    <input name="seal" type="hidden" value="..." />
    <input type="submit" value="Proceed to checkout"/>
</form>
```

### Verifying the payment
When your customer is done, he will be able to return to your application. This is done 
via a form, making a POST request to the `normalReturnUrl` provided during the initialization of your payment.
This POST request contains details on the payment. You can simply decode these responses, providing a `Map<String, String>`
of the parameters included in the received request to your `PaypageClient`:

```java
PaypageResponse paypageResponse = paypageClient.decodeResponse(mappedRequestParameters);
```
 
> :warning: Since the customer is not always redirecting back (e.g. he closes the confirmation page), it's
a good practice to include an `automaticResponseUrl`. SIPS will always POST details on the transaction on this URL,
even if a customer doesn't redirect back to your application.
