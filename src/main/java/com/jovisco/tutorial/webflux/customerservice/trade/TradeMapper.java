package com.jovisco.tutorial.webflux.customerservice.trade;

import com.jovisco.tutorial.webflux.customerservice.customer.Customer;

public class TradeMapper {

    public static TradeResponseDto toResponseDto(TradeRequestDto trade, Customer customer) {
        return new TradeResponseDto(
                customer.getId(),
                trade.price(),
                trade.quantity(),
                trade.action(),
                trade.totalPrice(),
                customer.getBalance()
        );
    }
}
