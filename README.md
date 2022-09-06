# Spring-Boot-REST-Oauth2-Retrofit
## How to build and run
Just run with maven
```
mvn clean package spring-boot:run
```
## How to use
You can use application through browser:

    Open http://localhost:8080 and authentication in authorization server

    Than go to http://localhost:8080/payment to do request to protected resource

Or you can use postman to make http://localhost:8080/api/v1/payment-requests

But you need to set Cookie JSESSIONID and produce this body, so this call for development purpose

```json
{
  "beneficiary": {
    "creditor": {
      "name": "string"
    },
    "creditorAccount": {
      "iban": "string"
    }
  },
  "creationDateTime": "string",
  "creditTransferTransaction": {
    "instructedAmount": {
      "amount": 0,
      "currency": "string"
    },
    "remittanceInformation": {
      "unstructured": "string"
    },
    "requestedExecutionDate": "string"
  },
  "numberOfTransactions": 0,
  "paymentInformationId": "string",
  "paymentTypeInformation": {
    "categoryPurpose": "string",
    "localInstrument": "string",
    "serviceLevel": "string"
  }
}
```
## Additional information
It was difficult to implement flow 
1. Show the 'payment page' with an input for Creditor IBAN, Creditor Name, amount, currency, description and submit button 
2. When user pressing 'submit' button, application starting authorization process against Smartym bank simulation 
3. Receive a token from Smartym bank simulation and make a payment initiation request

So I've implemented
1. Receive a token
2. Show the 'payment page
3. Submit

Maybe I need some more time to know how to override Spring Boot logic to implement flow 

Post method -> redirect to authorization server and get code -> get token -> comeback to first post with privies parameters

It would have been easier for me if I had used react redux for example to store state

