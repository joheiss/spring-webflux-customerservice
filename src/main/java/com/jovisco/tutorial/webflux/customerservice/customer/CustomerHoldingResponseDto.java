package com.jovisco.tutorial.webflux.customerservice.customer;

import com.jovisco.tutorial.webflux.customerservice.trade.Ticker;

public record CustomerHoldingResponseDto(
        Ticker ticker,
        Integer quantity
) {
}
