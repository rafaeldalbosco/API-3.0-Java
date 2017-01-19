# API-3.0-Java

SDK API-3.0 Java

## Principais recursos

* [x] Pagamentos por cartão de crédito.
* [x] Pagamentos recorrentes.
    * [x] Com autorização na primeira recorrência.
    * [x] Com autorização a partir da primeira recorrência.
* [x] Pagamentos por cartão de débito.
* [x] Pagamentos por boleto.
* [x] Pagamentos por transferência eletrônica.
* [x] Cancelamento de autorização.
* [x] Consulta de pagamentos.
* [x] Geração de token para o cartão para armazenamento seguro
 
## Limitações

Por envolver a interface de usuário da aplicação, o SDK funciona apenas como um framework para criação das transações. Nos casos onde a autorização é direta, não há limitação; mas nos casos onde é necessário a autenticação ou qualquer tipo de redirecionamento do usuário, o desenvolvedor deverá utilizar o SDK para gerar o pagamento e, com o link retornado pela Cielo, providenciar o redirecionamento do usuário.

## Utilizando o SDK

Para criar um pagamento simples com cartão de crédito com o SDK, basta fazer:

### Criando um pagamento com cartão de crédito

```java
// ...
// Configure seu merchant
Merchant merchant = new Merchant("MERCHANT ID", "MERCHANT KEY");

// Crie uma instância de Sale informando o ID do pagamento
Sale sale = new Sale("ID do pagamento");

// Crie uma instância de Customer informando o nome do cliente
Customer customer = sale.customer("Comprador Teste");

// Crie uma instância de Payment informando o valor do pagamento
Payment payment = sale.payment(15700);

// Crie  uma instância de Credit Card utilizando os dados de teste
// esses dados estão disponíveis no manual de integração
payment.creditCard("123", "Visa").setExpirationDate("12/2018")
                                 .setCardNumber("0000000000000001")
                                 .setHolder("Fulano de Tal");

// Crie o pagamento na Cielo
try {
    // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
    sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);

    // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
    // dados retornados pela Cielo
    String paymentId = sale.getPayment().getPaymentId();

    // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
    sale = new CieloEcommerce(merchant, Environment.SANDBOX).captureSale(paymentId, 15700, 0);

    // E também podemos fazer seu cancelamento, se for o caso
    sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, 15700);
} catch (CieloRequestException e) {
    // Em caso de erros de integração, podemos tratar o erro aqui.
    // os códigos de erro estão todos disponíveis no manual de integração.
    CieloError error = e.getError();
} catch (IOException e) {
	e.printStackTrace();
}
// ...
```

### Criando um card token para armazenamento seguro do cartão

```java
// Configure seu merchant
Merchant merchant = new Merchant("MERCHANT ID", "MERCHANT KEY");

// Informe os dados do cartão que irá tokenizar
CardToken cardToken = new CardToken().setBrand("Visa")
                                     .setCardNumber("4532117080573700")
                                     .setHolder("Comprador T Cielo")
                                     .setExpirationDate("12/2018");

// Crie o Token para o cartão
try {
	// // Configure o SDK com seu merchant e o ambiente apropriado para
	// gerar o token
	cardToken = new CieloEcommerce(merchant, Environment.SANDBOX).createCardToken(cardToken);
	
	String generatedToken = cardToken.getCardToken();

	System.out.printf("CardToken: %s\n", generatedToken);
} catch (CieloRequestException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}
```

## Manual

Para mais informações sobre a integração com a API 3.0 da Cielo, vide o manual em: [Integração API 3.0](https://developercielo.github.io/Webservice-3.0/)
