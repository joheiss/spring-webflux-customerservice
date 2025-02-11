package com.jovisco.tutorial.webflux.customerservice.portfolioitem;

import com.jovisco.tutorial.webflux.customerservice.trade.Ticker;

public record PortfolioItemResponseDto(
        Integer id,
        Integer customerId,
        Ticker ticker,
        Integer quantity
) {
}
