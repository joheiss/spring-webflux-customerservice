package com.jovisco.tutorial.webflux.customerservice.trade;

public record TradeResponseDto(
    Integer customerId,
    Integer price,
    Integer quantity,
    TradeAction action,
    Integer totalPrice,
    Integer balance
) {
}
