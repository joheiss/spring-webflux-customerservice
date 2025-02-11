package com.jovisco.tutorial.webflux.customerservice;

import com.jovisco.tutorial.webflux.customerservice.trade.Ticker;
import com.jovisco.tutorial.webflux.customerservice.trade.TradeAction;
import com.jovisco.tutorial.webflux.customerservice.trade.TradeRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@Slf4j
@AutoConfigureWebTestClient
@SpringBootTest
class CustomerserviceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testGetCustomerInformation() {
		getCustomerInformation(1, HttpStatus.OK)
				.jsonPath("$.name").isEqualTo("Sam")
				.jsonPath("$.balance").isEqualTo(10000)
				.jsonPath("$.holdings").isEmpty();
	}

	@Test
	public void testBuyAndSell() {
		// buy
		var buyRequest1 = new TradeRequestDto(Ticker.GOOGLE, 100, 5, TradeAction.BUY);
		createTrade(2, buyRequest1, HttpStatus.OK)
				.jsonPath("$.balance").isEqualTo(9500)
				.jsonPath("$.totalPrice").isEqualTo(500);
		var buyRequest2 = new TradeRequestDto(Ticker.GOOGLE, 100, 10, TradeAction.BUY);
		createTrade(2, buyRequest2, HttpStatus.OK)
				.jsonPath("$.balance").isEqualTo(8500)
				.jsonPath("$.totalPrice").isEqualTo(1000);

		// check the holdings
		getCustomerInformation(2, HttpStatus.OK)
				.jsonPath("$.holdings").isNotEmpty()
				.jsonPath("$.holdings.length()").isEqualTo(1) // intentional
				.jsonPath("$.holdings[0].ticker").isEqualTo("GOOGLE")
				.jsonPath("$.holdings[0].quantity").isEqualTo(15);

		// sell
		var sellRequest1 = new TradeRequestDto(Ticker.GOOGLE, 110, 5, TradeAction.SELL);
		createTrade(2, sellRequest1, HttpStatus.OK)
				.jsonPath("$.balance").isEqualTo(9050)
				.jsonPath("$.totalPrice").isEqualTo(550);
		var sellRequest2 = new TradeRequestDto(Ticker.GOOGLE, 110, 10, TradeAction.SELL);
		createTrade(2, sellRequest2, HttpStatus.OK)
				.jsonPath("$.balance").isEqualTo(10150)
				.jsonPath("$.totalPrice").isEqualTo(1100);

		// check the holdings
		getCustomerInformation(2, HttpStatus.OK)
				.jsonPath("$.holdings").isNotEmpty()
				.jsonPath("$.holdings.length()").isEqualTo(1) // intentional
				.jsonPath("$.holdings[0].ticker").isEqualTo("GOOGLE")
				.jsonPath("$.holdings[0].quantity").isEqualTo(0);
	}

	@Test
	public void testCustomerNotFound() {
		getCustomerInformation(10, HttpStatus.NOT_FOUND)
				.jsonPath("$.detail").isEqualTo("Customer [id=10] not found");

		var sellRequest = new TradeRequestDto(Ticker.GOOGLE, 110, 5, TradeAction.SELL);
		createTrade(10, sellRequest, HttpStatus.NOT_FOUND)
				.jsonPath("$.detail").isEqualTo("Customer [id=10] not found");
	}

	@Test
	public void testInsufficientBalance(){
		var buyRequest = new TradeRequestDto(Ticker.GOOGLE, 100, 101, TradeAction.BUY);
		createTrade(3, buyRequest, HttpStatus.BAD_REQUEST)
				.jsonPath("$.detail")
				.isEqualTo("Customer [id=3] does not have enough funds to complete the transaction");
	}

	@Test
	public void testInsufficientShares(){
		var sellRequest = new TradeRequestDto(Ticker.GOOGLE, 100, 1, TradeAction.SELL);
		createTrade(3, sellRequest, HttpStatus.BAD_REQUEST)
				.jsonPath("$.detail")
				.isEqualTo("Customer [id=3] does not have enough shares to complete the transaction");
	}

	private WebTestClient.BodyContentSpec getCustomerInformation(Integer customerId, HttpStatus expectedStatus) {
		return webTestClient.get()
				.uri("/customers/{customerId}", customerId)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus)
				.expectBody()
				.consumeWith(
						e -> log.info("{}",
							new String(Objects.requireNonNull(e.getResponseBody()))));
	}

	private WebTestClient.BodyContentSpec createTrade(Integer customerId, TradeRequestDto request, HttpStatus expectedStatus) {
		return webTestClient.post()
				.uri("/customers/{customerId}/trade", customerId)
				.bodyValue(request)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus)
				.expectBody()
				.consumeWith(
						e -> log.info("{}",
								new String(Objects.requireNonNull(e.getResponseBody()))));
	}

}
