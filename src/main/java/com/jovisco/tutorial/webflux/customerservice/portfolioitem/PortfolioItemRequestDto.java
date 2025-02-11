package com.jovisco.tutorial.webflux.customerservice.portfolioitem;

public record PortfolioItemRequestDto(
        Integer customerId,
        String ticker,
        Integer quantity
) {
}
