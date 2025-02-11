package com.jovisco.tutorial.webflux.customerservice.portfolioitem;

import com.jovisco.tutorial.webflux.customerservice.trade.Ticker;

public class PortfolioItemMapper {

    public static PortfolioItem toEntity(Integer customerId, Ticker ticker) {
        return PortfolioItem.builder()
                .customerId(customerId)
                .ticker(ticker)
                .quantity(0)
                .build();
    }

    public static PortfolioItemResponseDto toResponseDto(PortfolioItem portfolioItem) {
        return new PortfolioItemResponseDto(
                portfolioItem.getId(),
                portfolioItem.getCustomerId(),
                portfolioItem.getTicker(),
                portfolioItem.getQuantity()
        );
    }
}
