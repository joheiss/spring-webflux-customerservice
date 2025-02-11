package com.jovisco.tutorial.webflux.customerservice.trade;

public record TradeRequestDto(
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action
) {
    public Integer totalPrice() {
        return price * quantity;
    }
}
